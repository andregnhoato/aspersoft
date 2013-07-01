package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.dao.MercadoriaDAO;
import dr.dao.MercadoriaDAOJPA;
import dr.event.BuscarMercadoriaEvent;
import dr.model.Mercadoria;
import dr.ui.BuscarMercadoriaView;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de Busca de <code>Mercadoria</code> pelo campo <code>nome</code>.
 * 
 * @see br.com.yaw.jfx.controller.PersistenceController
 * 
 * @author YaW Tecnologia
 */
public class BuscarMercadoriaController extends PersistenceController {
    
    private BuscarMercadoriaView view;
    
    public BuscarMercadoriaController(ListaMercadoriaController parent) {
        super(parent);
        this.view = new BuscarMercadoriaView();
        
        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                BuscarMercadoriaController.this.cleanUp();
            }
        });
        
        registerAction(this.view.getCancelarButton(), new AbstractAction() {
            @Override
            protected void action() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.hide();
                    }
                });
            }
        });
        
        registerAction(view.getBuscarButton(), ConditionalAction.build()
                .addConditional(new BooleanExpression() {
                    @Override
                    public boolean conditional() {
                        return view.getText().length() > 0;
                    }
                })
                .addAction(new AbstractAction() {
                    private List<Mercadoria> list;

                    @Override
                    protected void action() {
                        MercadoriaDAO dao = new MercadoriaDAOJPA(getPersistenceContext());
                        list = dao.getMercadoriasByNome(view.getText());
                    }

                    @Override
                    public void posAction() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                view.hide();
                            }
                        });
                        fireEvent(new BuscarMercadoriaEvent(list));
                        list = null;
                    }
                }));
    }
    
    public void show() {
        loadPersistenceContext();
        view.show();
    }

    @Override
    protected void cleanUp() {
        view.resetForm();
	super.cleanUp();
    }
}
