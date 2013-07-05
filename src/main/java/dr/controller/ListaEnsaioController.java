package dr.controller;

import dr.action.AbstractAction;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOJPA;
import dr.event.AbstractEventListener;
import dr.event.BuscarMercadoriaEvent;
import dr.event.ensaio.IncluirEnsaioEvent;
import dr.event.ensaio.RemoveEnsaioEvent;
import dr.event.ensaio.AtualizaListaEnsaioEvent;
import dr.event.ensaio.BuscarEnsaioEvent;
import dr.model.Ensaio;
import dr.model.Mercadoria;
import dr.ui.ensaio.EnsaioListView;
import dr.util.JPAUtil;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Ensaio</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @andre
 */
public class ListaEnsaioController extends PersistenceController {

    private EnsaioListView view;
    private IncluirEnsaioController addEnsaioController;
    private BuscarEnsaioController buscarController;

    public ListaEnsaioController(AbstractController parent) {
        super(parent);
        loadPersistenceContext();
        this.view = new EnsaioListView();
        this.addEnsaioController = new IncluirEnsaioController(this);
        this.buscarController = new BuscarEnsaioController(this);
        
        registerAction(view.getNewButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaEnsaioController.this.addEnsaioController.show();
            }
        });
        
        registerAction(view.getFindButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaEnsaioController.this.buscarController.show();
            }
        });
        
        registerAction(view.getRefreshButton(), new AbstractAction() {
            @Override
            protected void action() {
                refreshTable();
            }
        });
        
        view.getTable().setMouseEvent(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    Ensaio e = view.getTable().getEnsaioSelected();
                    if (e != null) {
                        ListaEnsaioController.this.addEnsaioController.show(e);
                    }
                }
            }
        });
        
        registerEventListener(IncluirEnsaioEvent.class, new AbstractEventListener<IncluirEnsaioEvent>() {
            @Override
            public void handleEvent(IncluirEnsaioEvent event) {
                refreshTable();
            }
        });
        
        registerEventListener(RemoveEnsaioEvent.class, new AbstractEventListener<RemoveEnsaioEvent>() {
            @Override
            public void handleEvent(RemoveEnsaioEvent event) {
                refreshTable();
            }
        });
        
        registerEventListener(AtualizaListaEnsaioEvent.class, new AbstractEventListener<AtualizaListaEnsaioEvent>() {
            @Override
            public void handleEvent(AtualizaListaEnsaioEvent event) {
                refreshTable();
            }
        });
        
        registerEventListener(BuscarEnsaioEvent.class, new AbstractEventListener<BuscarEnsaioEvent>() {
            @Override
            public void handleEvent(BuscarEnsaioEvent event) {
                List<Ensaio> list = event.getTarget();
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
    
    @Override
    public void cleanUp() {
        super.cleanUp();
        JPAUtil.closeEntityManagerFactory();
    }
    
    private void refreshTable() {
        refreshTable(null);
    }
    
    private void refreshTable(List<Ensaio> list) {
        //view.addTransition();
        if (list != null) {
            view.refreshTable(list);
            return;
        }
        
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                EnsaioDAO dao = new EnsaioDAOJPA(getPersistenceContext());
                view.refreshTable(dao.getAll());
            }
        });
    }
}
