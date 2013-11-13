package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.dao.BocalDAO;
import dr.dao.BocalDAOImpl;
import dr.event.BuscarBocalEvent;
import dr.model.Bocal;
import dr.ui.bocal.BuscarBocalView;
import dr.util.DateUtil;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de Busca de <code>Bocal</code> pelo campo <code>nome</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @Andre
 */
public class BuscarBocalController extends PersistenceController {
    
    private BuscarBocalView view;
    
    public BuscarBocalController(ListaBocalController parent) {
        super(parent);
        this.view = new BuscarBocalView();
        
        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                BuscarBocalController.this.cleanUp();
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
                    private List<Bocal> list;

                    @Override
                    protected void action() {
                        
                        //IMPLEMENTAR BUSCA DE BOCAL PELA DESCRICAO
                        
//                        BocalDAO dao = new BocalDAOImpl(getPersistenceContext());
//                        StringBuilder sb = new StringBuilder();
//                        sb.append("WHERE 1=1 ");
//                        if(view.getText().length()>0)
//                            sb.append(" and e.descricao like ").append("'%").append(view.getText()).append("%'");
//                        if(view.getDataInicio()!=null)
//                            sb.append(" and e.data > ").append(DateUtil.formatDate(view.getDataInicio())).append("'");
//                        if(view.getDataFim()!=null)
//                            sb.append(" and e.data < '").append(DateUtil.formatDate(view.getDataFim())).append("'");
//                        
//                        
//                        list = dao.getBocalsByWhere(sb.toString());
                    }

                    @Override
                    public void posAction() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                view.hide();
                            }
                        });
                        fireEvent(new BuscarBocalEvent(list));
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
