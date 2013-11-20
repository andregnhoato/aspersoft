package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.dao.CombinacaoDAO;
import dr.dao.CombinacaoDAOImpl;
import dr.event.BuscarCombinacaoEvent;
import dr.model.Combinacao;
import dr.ui.combinacao.BuscarCombinacaoView;
import dr.util.DateUtil;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de Busca de <code>Combinacao</code> pelo campo <code>nome</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @Andre
 */
public class BuscarCombinacaoController extends PersistenceController {
    
    private BuscarCombinacaoView view;
    
    public BuscarCombinacaoController(ListaCombinacaoController parent) {
        super(parent);
        this.view = new BuscarCombinacaoView();
        
        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                BuscarCombinacaoController.this.cleanUp();
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
                    private List<Combinacao> list;

                    @Override
                    protected void action() {
                        CombinacaoDAO dao = new CombinacaoDAOImpl(getPersistenceContext());
                        StringBuilder sb = new StringBuilder();
                        sb.append("WHERE 1=1 ");
                        if(view.getText().length()>0)
                            sb.append(" and e.descricao like ").append("'%").append(view.getText()).append("%'");
                        if(view.getDataInicio()!=null)
                            sb.append(" and e.data > ").append(DateUtil.formatDate(view.getDataInicio())).append("'");
                        if(view.getDataFim()!=null)
                            sb.append(" and e.data < '").append(DateUtil.formatDate(view.getDataFim())).append("'");
                        
                        
                        list = dao.getCombinacaoByDescricao(sb.toString());
                    }

                    @Override
                    public void posAction() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                view.hide();
                            }
                        });
                        fireEvent(new BuscarCombinacaoEvent(list));
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
