package dr.controller;

import dr.action.AbstractAction;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOImpl;
import dr.event.AbstractEventListener;
import dr.event.IncluirEnsaioEvent;
import dr.event.RemoveEnsaioEvent;
import dr.event.AtualizaListaEnsaioEvent;
import dr.event.BuscarEnsaioEvent;
import dr.model.Coleta;
import dr.model.Ensaio;
import dr.ui.Dialog;
import dr.ui.ensaio.EnsaioListView;
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
public class ListaEnsaioController extends PersistenceController {

    protected EnsaioListView view;
    private IncluirEnsaioController addEnsaioController;
    private BuscarEnsaioController buscarController;
    private ListaColetaController coletaController;
    private AnaliseController uniformidadeController;

    public ListaEnsaioController(AbstractController parent) {
        super(parent);
        loadPersistenceContext();
        this.view = new EnsaioListView();
        this.addEnsaioController = new IncluirEnsaioController(this);
        this.buscarController = new BuscarEnsaioController(this);
        this.coletaController = new ListaColetaController(this);
        this.uniformidadeController = new AnaliseController(this);


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

        registerAction(view.getColetaButton(), new AbstractAction() {
            @Override
            protected void action() {
                Ensaio e = view.getTable().getEnsaioSelected();
                if (e != null) {
                    /*alterar pelo controller de coleta ainda não implementado*/
                    ListaEnsaioController.this.coletaController.setEnsaio(e);
                    ListaEnsaioController.this.coletaController.reRenderTable();
                    ListaEnsaioController.this.coletaController.show();
                } else {
                    Dialog.showInfo("Validacão", "Selecione um Ensaio", view);
                }
            }
        });

        registerAction(view.getUniformidadeButton(), new AbstractAction() {
            @Override
            protected void action() {
                Ensaio e = view.getTable().getEnsaioSelected();
                if (e != null) {
                    try {
                        ColetaDAO dao = new ColetaDAOImpl(JPAUtil.getEntityManager());
                        List<Coleta> coletas = dao.findColetasByEnsaio(e);

                        if (coletas.size() > 0) {
                            ListaEnsaioController.this.uniformidadeController.setEnsaio(e);
                            ListaEnsaioController.this.uniformidadeController.reRenderTable();
                            ListaEnsaioController.this.uniformidadeController.show();
                        }else
                            Dialog.showInfo("Validacão", "O ensaio selecionado não possui os valores das coletas, necessário para a tela de Análise.", view);
                    } catch (Exception ex) {
                        Logger.getLogger(ListaEnsaioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Dialog.showInfo("Validacão", "Selecione um Ensaio", view);
                }
            }
        });

        view.getTable().setMouseEvent(new EventHandler<MouseEvent>() {
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
                    EnsaioDAO dao = new EnsaioDAOImpl(JPAUtil.getEntityManager());
                    view.refreshTable(dao.findAll());
                } catch (Exception ex) {
                    Logger.getLogger(ListaEnsaioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
