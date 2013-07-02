package dr.controller;

import dr.action.AbstractAction;
import dr.ui.MainView;
import dr.ui.SobreView;
import dr.util.JPAUtil;
import javafx.stage.Stage;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Ensaio</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @andre
 */
public class MainController extends PersistenceController {

    //private ListaMercadoriasView view;
    private IncluirEnsaioController addEnsaioController;
    private ListaEnsaioController ensaioListController;
    //private BuscarMercadoriaController buscarController;
    private MainView mainView;

    public MainController(final Stage mainStage) {
        loadPersistenceContext();
        this.mainView = new MainView(mainStage);
        this.addEnsaioController = new IncluirEnsaioController(this);
        this.ensaioListController = new ListaEnsaioController(this);
        //this.incluirController = new IncluirMercadoriaController(this);
        //this.buscarController = new BuscarMercadoriaController(this);
        
        final SobreView sobreView = new SobreView();
        
        registerAction(mainView.getMenuEnsaio(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.addEnsaioController.show();
            }
        });
        
        registerAction(mainView.getMenuEnsaioList(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.ensaioListController.show();
            }
        });
        /*
        registerAction(view.getFindButton(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.buscarController.show();
            }
        });
        
        registerAction(view.getRefreshButton(), new AbstractAction() {
            @Override
            protected void action() {
                refreshTable();
            }
        });
        */
        registerAction(mainView.getMenuAbout(), new AbstractAction() {
            @Override
            public void action() {
                sobreView.show();
            }
        });
        /*
        view.getTabela().setMouseEvent(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    Mercadoria m = view.getTabela().getMercadoriaSelected();
                    if (m != null) {
                        MainController.this.incluirController.show(m);
                    }
                }
            }
        });
        
        registerEventListener(IncluirMercadoriaEvent.class, new AbstractEventListener<IncluirMercadoriaEvent>() {
            @Override
            public void handleEvent(IncluirMercadoriaEvent event) {
                refreshTable();
            }
        });
        
        registerEventListener(DeletarMercadoriaEvent.class, new AbstractEventListener<DeletarMercadoriaEvent>() {
            @Override
            public void handleEvent(DeletarMercadoriaEvent event) {
                refreshTable();
            }
        });
        
        registerEventListener(AtualizarListarMercadoriaEvent.class, new AbstractEventListener<AtualizarListarMercadoriaEvent>() {
            @Override
            public void handleEvent(AtualizarListarMercadoriaEvent event) {
                refreshTable();
            }
        });
        
        registerEventListener(BuscarMercadoriaEvent.class, new AbstractEventListener<BuscarMercadoriaEvent>() {
            @Override
            public void handleEvent(BuscarMercadoriaEvent event) {
                List<Mercadoria> list = event.getTarget();
                if (list != null) {
                    refreshTable(event.getTarget());
                }
            }
        });
        
        refreshTable();
         */
    }
    
    @Override
    public void cleanUp() {
        super.cleanUp();
        JPAUtil.closeEntityManagerFactory();
    }
    /*s
    private void refreshTable() {
        //refreshTable(null);
    }
    /*
    private void refreshTable(List<Mercadoria> list) {
        view.addTransition();
        if (list != null) {
            view.refreshTable(list);
            return;
        }
        
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                MercadoriaDAO dao = new MercadoriaDAOJPA(getPersistenceContext());
                view.refreshTable(dao.getAll());
            }
        });
    }
    */
}
