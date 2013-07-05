package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOJPA;
import dr.event.ensaio.BuscarEnsaioEvent;
import dr.model.Ensaio;
import dr.ui.ensaio.BuscarEnsaioView;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de Busca de <code>Ensaio</code> pelo campo <code>nome</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @Andre
 */
public class BuscarEnsaioController extends PersistenceController {
    
    private BuscarEnsaioView view;
    
    public BuscarEnsaioController(ListaEnsaioController parent) {
        super(parent);
        this.view = new BuscarEnsaioView();
        
        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                BuscarEnsaioController.this.cleanUp();
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
                    private List<Ensaio> list;

                    @Override
                    protected void action() {
                        EnsaioDAO dao = new EnsaioDAOJPA(getPersistenceContext());
                        list = dao.getEnsaiosByDescricao(view.getText());
                    }

                    @Override
                    public void posAction() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                view.hide();
                            }
                        });
                        fireEvent(new BuscarEnsaioEvent(list));
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
