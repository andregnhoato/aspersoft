package dr.controller;

import dr.action.AbstractAction;
import dr.dao.BocalDAO;
import dr.dao.BocalDAOImpl;
import dr.event.AbstractEventListener;
import dr.event.AtualizaListaBocalEvent;
import dr.event.BuscarBocalEvent;
import dr.event.IncluirBocalEvent;
import dr.event.RemoveBocalEvent;
import dr.model.Bocal;
import dr.ui.Dialog;
import dr.ui.bocal.BocalListView;
import dr.ui.combinacao.IncluirCombinacaoView;
import dr.ui.ensaio.EnsaioListView;
import dr.ui.ensaio.IncluirEnsaioView;
import dr.util.JPAUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Define a
 * <code>Controller</code> principal do sistema, responsável por gerir a tela
 * com a lista de
 * <code>Bocal</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @andre
 */
public class ListaBocalController extends PersistenceController {

    private BocalListView view;
    private IncluirBocalController addBocalController;
    private BuscarBocalController buscarController;
    private Bocal bocal;
    private IncluirEnsaioView Iview;
    private IncluirCombinacaoView Cview;
    private EnsaioListView Eview;
    

    public ListaBocalController(AbstractController parent) {
        super(parent);
        loadPersistenceContext();
        this.view = new BocalListView();
        this.addBocalController = new IncluirBocalController(this);
        this.buscarController = new BuscarBocalController(this);
        


        registerAction(view.getNewButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaBocalController.this.addBocalController.show();
            }
        });

        registerAction(view.getFindButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaBocalController.this.buscarController.show();
            }
        });

        registerAction(view.getRefreshButton(), new AbstractAction() {
            @Override
            protected void action() {
                refreshTable();
            }
        });
        
        registerAction(view.getEditBocal(), new AbstractAction() {

            @Override
            protected void action() {
                bocal =  view.getTable().getBocalSelected();
                if(bocal!=null)
                    ListaBocalController.this.addBocalController.show(bocal);
                else
                    Dialog.showError("Validação", "Selecione um bocal na listagem para realizar a edição.");
            }
        });

       
        view.getTable().setMouseEvent(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    if(Iview != null)
                        Iview.setBocal(view.getTable().getBocalSelected());
                    if(Cview != null)
                        Cview.setBocal(view.getTable().getBocalSelected());
                    if(Eview!=null)
                        Eview.setBocal(view.getTable().getBocalSelected());
                    
                    view.hide();
                    
//                    if (e != null) {
//                        ListaBocalController.this.addBocalController.show(e);
//                    }
                }
            }
        });

        registerEventListener(IncluirBocalEvent.class, new AbstractEventListener<IncluirBocalEvent>() {
            @Override
            public void handleEvent(IncluirBocalEvent event) {
                refreshTable();
            }
        });

        registerEventListener(RemoveBocalEvent.class, new AbstractEventListener<RemoveBocalEvent>() {
            @Override
            public void handleEvent(RemoveBocalEvent event) {
                refreshTable();
            }
        });

        registerEventListener(AtualizaListaBocalEvent.class, new AbstractEventListener<AtualizaListaBocalEvent>() {
            @Override
            public void handleEvent(AtualizaListaBocalEvent event) {
                refreshTable();
            }
        });

        registerEventListener(BuscarBocalEvent.class, new AbstractEventListener<BuscarBocalEvent>() {
            @Override
            public void handleEvent(BuscarBocalEvent event) {
                List<Bocal> list = event.getTarget();
                if (list != null) {
                    refreshTable(event.getTarget());
                }
            }
        });

        refreshTable();
    }

    public void show() {
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }
    
    public void show(IncluirEnsaioView v) {
        this.Iview = v;
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }
    
    public void show(IncluirCombinacaoView v) {
        this.Cview = v;
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }
    
    public void show(EnsaioListView v) {
        this.Eview = v;
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }

    @Override
    public void cleanUp() {
//        super.cleanUp();
        JPAUtil.closeEntityManagerFactory();
    }

    private void refreshTable() {
        refreshTable(null);
    }

    private void refreshTable(List<Bocal> list) {
        if (list != null) {
            view.refreshTable(list);
            return;
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (view == null) {
                    Logger.getLogger("nulo a parada da view");
                }
                try {
                    BocalDAO dao = new BocalDAOImpl(JPAUtil.getEntityManager());
                    view.refreshTable(dao.findAll());
                } catch (Exception ex) {
                    Logger.getLogger(ListaBocalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public Bocal getBocal() {
        return bocal;
    }

    public void setBocal(Bocal bocal) {
        this.bocal = bocal;
    }
    
}
