package dr.controller;

import dr.action.AbstractAction;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.dao.CombinacaoDAO;
import dr.dao.CombinacaoDAOImpl;
import dr.event.AbstractEventListener;
import dr.event.IncluirCombinacaoEvent;
import dr.event.RemoveCombinacaoEvent;
import dr.event.AtualizaListaCombinacaoEvent;
import dr.event.BuscarCombinacaoEvent;
import dr.model.Coleta;
import dr.model.Combinacao;
import dr.ui.Dialog;
import dr.ui.combinacao.CombinacaoListView;
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
 * <code>Combinacao</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @andre
 */
public class ListaCombinacaoController extends PersistenceController {

    private CombinacaoListView view;
    private IncluirCombinacaoController addCombinacaoController;
    private BuscarCombinacaoController buscarController;

    public ListaCombinacaoController(AbstractController parent) {
        super(parent);
        loadPersistenceContext();
        this.view = new CombinacaoListView();
        this.addCombinacaoController = new IncluirCombinacaoController(this);
        this.buscarController = new BuscarCombinacaoController(this);
        
        registerAction(view.getNewButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaCombinacaoController.this.addCombinacaoController.show();
            }
        });

        registerAction(view.getFindButton(), new AbstractAction() {
            @Override
            protected void action() {
                ListaCombinacaoController.this.buscarController.show();
            }
        });

        registerAction(view.getRefreshButton(), new AbstractAction() {
            @Override
            protected void action() {
                refreshTable();
            }
        });

//        TODO: BOTÃO DE IMPRIMIR A TABELA
//        registerAction(view.getUniformidadeButton(), new AbstractAction() {
//            @Override
//            protected void action() {
//                Combinacao e = view.getTable().getCombinacaoSelected();
//                if (e != null) {
//                    try {
//                        ColetaDAO dao = new ColetaDAOImpl(JPAUtil.getEntityManager());
//                        List<Coleta> coletas = dao.findColetasByCombinacao(e);
//
//                        if (coletas.size() > 0) {
//                            ListaCombinacaoController.this.uniformidadeController.setCombinacao(e);
//                            ListaCombinacaoController.this.uniformidadeController.reRenderTable();
//                            ListaCombinacaoController.this.uniformidadeController.show();
//                        }else
//                            Dialog.showInfo("Validacão", "O ensaio selecionado não possui os valores das coletas, necessário para a tela de Análise.", view);
//                    } catch (Exception ex) {
//                        Logger.getLogger(ListaCombinacaoController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } else {
//                    Dialog.showInfo("Validacão", "Selecione um Combinacao", view);
//                }
//            }
//        });

        view.getTable().setMouseEvent(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    Combinacao c = view.getTable().getCombinacaoSelected();
                    if (c != null) {
                        ListaCombinacaoController.this.addCombinacaoController.show(c);
                    }
                }
            }
        });

        registerEventListener(IncluirCombinacaoEvent.class, new AbstractEventListener<IncluirCombinacaoEvent>() {
            @Override
            public void handleEvent(IncluirCombinacaoEvent event) {
                refreshTable();
            }
        });

        registerEventListener(RemoveCombinacaoEvent.class, new AbstractEventListener<RemoveCombinacaoEvent>() {
            @Override
            public void handleEvent(RemoveCombinacaoEvent event) {
                refreshTable();
            }
        });

        registerEventListener(AtualizaListaCombinacaoEvent.class, new AbstractEventListener<AtualizaListaCombinacaoEvent>() {
            @Override
            public void handleEvent(AtualizaListaCombinacaoEvent event) {
                refreshTable();
            }
        });

        registerEventListener(BuscarCombinacaoEvent.class, new AbstractEventListener<BuscarCombinacaoEvent>() {
            @Override
            public void handleEvent(BuscarCombinacaoEvent event) {
                List<Combinacao> list = event.getTarget();
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

    private void refreshTable(List<Combinacao> list) {
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
                    CombinacaoDAO dao = new CombinacaoDAOImpl(JPAUtil.getEntityManager());
                    view.refreshTable(dao.findAll());
                } catch (Exception ex) {
                    Logger.getLogger(ListaCombinacaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
