package dr.controller;

import dr.action.AbstractAction;
import dr.dao.MercadoriaDAO;
import dr.dao.MercadoriaDAOJPA;
import dr.event.AbstractEventListener;
import dr.event.AtualizarListarMercadoriaEvent;
import dr.event.BuscarMercadoriaEvent;
import dr.event.DeletarMercadoriaEvent;
import dr.event.IncluirMercadoriaEvent;
import dr.model.Mercadoria;
import dr.ui.ListaMercadoriasView;
import dr.ui.SobreView;
import dr.util.JPAUtil;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.jfx.controller.PersistenceController
 * 
 * @author YaW Tecnologia
 */
public class ListaMercadoriaController extends PersistenceController {

    private ListaMercadoriasView view;
    private IncluirMercadoriaController incluirController;
    private BuscarMercadoriaController buscarController;

    public ListaMercadoriaController(final Stage mainStage) {
        loadPersistenceContext();
        this.view = new ListaMercadoriasView(mainStage);
        this.incluirController = new IncluirMercadoriaController(this);
        this.buscarController = new BuscarMercadoriaController(this);
        
        final SobreView sobreView = new SobreView();
        
        registerAction(view.getNewButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaMercadoriaController.this.incluirController.show();
            }
        });
        
        registerAction(view.getFindButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaMercadoriaController.this.buscarController.show();
            }
        });
        
        registerAction(view.getRefreshButton(), new AbstractAction() {
            @Override
            protected void action() {
                refreshTable();
            }
        });
        
        registerAction(view.getMenuSobre(), new AbstractAction() {
            @Override
            public void action() {
                sobreView.show();
            }
        });
        
        view.getTabela().setMouseEvent(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    Mercadoria m = view.getTabela().getMercadoriaSelected();
                    if (m != null) {
                        ListaMercadoriaController.this.incluirController.show(m);
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
    }
    
    @Override
    public void cleanUp() {
        super.cleanUp();
        JPAUtil.closeEntityManagerFactory();
    }
    
    private void refreshTable() {
        refreshTable(null);
    }
    
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
}
