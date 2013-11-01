package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOImpl;
import dr.event.ensaio.BuscarEnsaioEvent;
import dr.model.Ensaio;
import dr.ui.ensaio.BuscarEnsaioView;
import dr.util.DateUtil;
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
                        
                        return view.getText().length() > 0 || view.getDataInicio()!= null || view.getDataFim()!=null;
                    }
                })
                .addAction(new AbstractAction() {
                    private List<Ensaio> list;

                    @Override
                    protected void action() {
                        EnsaioDAO dao = new EnsaioDAOImpl(getPersistenceContext());
                        StringBuilder sb = new StringBuilder();
                        sb.append("WHERE 1=1 ");
                        if(view.getText().length()>0)
                            sb.append(" and e.descricao like ").append("'%").append(view.getText()).append("%'");
                        if(view.getDataInicio()!=null)
                            sb.append(" and e.data > ").append(DateUtil.formatDate(view.getDataInicio())).append("'");
                        if(view.getDataFim()!=null)
                            sb.append(" and e.data < '").append(DateUtil.formatDate(view.getDataFim())).append("'");
                        
                        
                        list = dao.getEnsaiosByWhere(sb.toString());
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
//	super.cleanUp();
    }
}
