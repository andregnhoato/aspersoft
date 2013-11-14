package dr.controller;

import dr.action.AbstractAction;
import dr.dao.QuebraJatoDAO;
import dr.dao.QuebraJatoDAOImpl;
import dr.event.AbstractEventListener;
import dr.event.AtualizaListaQuebraJatoEvent;
import dr.event.BuscarQuebraJatoEvent;
import dr.event.IncluirQuebraJatoEvent;
import dr.event.RemoveQuebraJatoEvent;
import dr.model.QuebraJato;
import dr.ui.quebraJato.QuebraJatoListView;
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
 * <code>QuebraJato</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @andre
 */
public class ListaQuebraJatoController extends PersistenceController {

    private QuebraJatoListView view;
    private IncluirQuebraJatoController addQuebraJatoController;
    private BuscarQuebraJatoController buscarController;
    private QuebraJato quebraJato;
    

    public ListaQuebraJatoController(AbstractController parent) {
        super(parent);
        loadPersistenceContext();
        this.view = new QuebraJatoListView();
        this.addQuebraJatoController = new IncluirQuebraJatoController(this);
        this.buscarController = new BuscarQuebraJatoController(this);
        


        registerAction(view.getNewButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaQuebraJatoController.this.addQuebraJatoController.show();
            }
        });

        registerAction(view.getFindButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaQuebraJatoController.this.buscarController.show();
            }
        });

        registerAction(view.getRefreshButton(), new AbstractAction() {
            @Override
            protected void action() {
                refreshTable();
            }
        });

       
        view.getTable().setMouseEvent(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    quebraJato = view.getTable().getQuebraJatoSelected();
                    view.hide();
//                    if (qj != null) {
//                        ListaQuebraJatoController.this.addQuebraJatoController.show(qj);
//                    }
                }
            }
        });

        registerEventListener(IncluirQuebraJatoEvent.class, new AbstractEventListener<IncluirQuebraJatoEvent>() {
            @Override
            public void handleEvent(IncluirQuebraJatoEvent event) {
                refreshTable();
            }
        });

        registerEventListener(RemoveQuebraJatoEvent.class, new AbstractEventListener<RemoveQuebraJatoEvent>() {
            @Override
            public void handleEvent(RemoveQuebraJatoEvent event) {
                refreshTable();
            }
        });

        registerEventListener(AtualizaListaQuebraJatoEvent.class, new AbstractEventListener<AtualizaListaQuebraJatoEvent>() {
            @Override
            public void handleEvent(AtualizaListaQuebraJatoEvent event) {
                refreshTable();
            }
        });

        registerEventListener(BuscarQuebraJatoEvent.class, new AbstractEventListener<BuscarQuebraJatoEvent>() {
            @Override
            public void handleEvent(BuscarQuebraJatoEvent event) {
                List<QuebraJato> list = event.getTarget();
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

    private void refreshTable(List<QuebraJato> list) {
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
                    QuebraJatoDAO dao = new QuebraJatoDAOImpl(JPAUtil.getEntityManager());
                    view.refreshTable(dao.findAll());
                } catch (Exception ex) {
                    Logger.getLogger(ListaQuebraJatoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public QuebraJato getQuebraJato() {
        return quebraJato;
    }

    public void setQuebraJato(QuebraJato quebraJato) {
        this.quebraJato = quebraJato;
    }
    
}
