package dr.controller;

import dr.action.AbstractAction;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOImpl;
import dr.event.AbstractEventListener;
import dr.event.AtualizaListaEnsaioEvent;
import dr.event.BuscarEnsaioEvent;
import dr.model.Ensaio;
import dr.ui.Dialog;
import dr.ui.perfil.PerfilView;
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
 * <code>Ensaio</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @andre
 */
public class PerfilController extends ListaEnsaioController {

    private PerfilView pView;

    public PerfilController(AbstractController parent) {
        super(parent);
        this.pView = new PerfilView();
        this.pView.refreshTable(null);

        registerAction(pView.getBtLimpar(), new AbstractAction() {
            @Override
            protected void action() {
                pView.limparGrafico();
            }
        });
        
        registerAction(pView.getBtExportarExcel(), new AbstractAction() {
            @Override
            protected void action() {
                Dialog.showError("Erro", "Não implementado");
            }
        });


        pView.getTable().setMouseEvent(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    Ensaio e = pView.getTable().getEnsaioSelected();
                    if (e != null) {
                        pView.setEnsaio(e);
                        pView.reRenderTable();
                    }
                }

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

    @Override
    public void show() {
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        pView.show();
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
        if (list != null) {
            pView.refreshTable(list);
            return;
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (pView == null) {
                    Logger.getLogger("nulo a parada da view");
                }
                try {
                    EnsaioDAO dao = new EnsaioDAOImpl(JPAUtil.getEntityManager());
                    pView.refreshTable(dao.findAll());
                } catch (Exception ex) {
                    Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
