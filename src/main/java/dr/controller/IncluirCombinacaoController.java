package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.action.TransactionalAction;
import dr.dao.CombinacaoDAO;
import dr.dao.CombinacaoDAOImpl;
import dr.event.IncluirCombinacaoEvent;
import dr.event.RemoveCombinacaoEvent;
import dr.model.Combinacao;
import dr.ui.Dialog;
import dr.ui.combinacao.IncluirCombinacaoView;
import dr.validation.CombinacaoValidator;
import dr.validation.Validator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a
 * <code>Controller</code> responsável por gerir a tela de inclusão/edição de
 * <code>Combinacao</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @Andre
 */
public class IncluirCombinacaoController extends PersistenceController {

    private IncluirCombinacaoView view;
    private Validator<Combinacao> validador = new CombinacaoValidator();
    static Boolean dialog;
    private ListaBocalController bocalListController;
    private ListaQuebraJatoController quebraListController;
    private Combinacao c;

    public IncluirCombinacaoController(AbstractController parent) {
        super(parent);

        this.view = new IncluirCombinacaoView();
        this.bocalListController = new ListaBocalController(this);
        this.quebraListController = new ListaQuebraJatoController(this);


        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                IncluirCombinacaoController.this.cleanUp();
            }
        });

        registerAction(this.view.getCancelButton(), new AbstractAction() {
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

        registerAction(this.view.getBocalButton(), new AbstractAction() {
            @Override
            protected void action() {
                bocalListController.show(view);
            }
  
        });
        
        
        registerAction(this.view.getQuebraButton(), new AbstractAction() {
            @Override
            protected void action() {
                quebraListController.show(view);
            }
           
        });

        registerAction(this.view.getSaveButton(),
                ConditionalAction.build()
                .addConditional(new BooleanExpression() {
            @Override
            public boolean conditional() {
                Combinacao e = view.getCombinacao();
                String msg = validador.validate(e);
                if (!"".equals(msg == null ? "" : msg)) {
                    Dialog.showInfo("Validacão", msg, view);
                    return false;
                }

                if (e.getBocal() == null) {
                    Dialog.showError("Validação", "Campo Bocal obrigatório, informar um valor.");
                    return false;
                }

                if (e.getQuebraJato() == null) {
                    Dialog.showError("Validação", "Campo Quebra Jato obrigatório, informar um valor.");
                    return false;
                }

                return true;
            }
        })
                .addAction(
                TransactionalAction.build()
                .persistenceCtxOwner(IncluirCombinacaoController.this)
                .addAction(new AbstractAction() {
            @Override
            protected void action() {
                c = view.getCombinacao();
                CombinacaoDAO dao = new CombinacaoDAOImpl(getPersistenceContext());
                try {
                    c = dao.save(c);
                } catch (Exception ex) {
                    Logger.getLogger(IncluirCombinacaoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            protected void posAction() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.hide();
                    }
                });
                fireEvent(new IncluirCombinacaoEvent(c));
            }
        })));

        registerAction(view.getRemoveButton(),
                ConditionalAction.build()
                .addConditional(new BooleanExpression() {
            @Override
            public boolean conditional() {

                return true;
            }
        })
               .addAction(
                new AbstractAction() {
            

            @Override
            protected void action() {

//
//                if (dialog!= null && dialog) {
                Integer id = view.getCombinacaoId();
                if (id != null) {
                    try {

                        CombinacaoDAO dao = new CombinacaoDAOImpl(getPersistenceContext());
                        c = dao.findById(id);
                        if (c != null) {
                            dao.remove(c);
//                                dialog = false;

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(IncluirCombinacaoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
//            }

            @Override
            public void posAction() {
                view.hide();
                fireEvent(new RemoveCombinacaoEvent(c));
            }
        }));
    }

    public void show() {
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }

    public void show(Combinacao e) {
        view.setCombinacao(e);
        view.setTitle("Editar Combinação de bocais");
        show();
    }

    @Override
    protected void cleanUp() {
        view.setTitle("Incluir Combinação");
        view.resetForm();

//        super.cleanUp();
    }
}
