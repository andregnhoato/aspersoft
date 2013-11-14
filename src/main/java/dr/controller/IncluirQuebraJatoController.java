package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.action.TransactionalAction;
import dr.dao.QuebraJatoDAO;
import dr.dao.QuebraJatoDAOImpl;
import dr.event.IncluirQuebraJatoEvent;
import dr.event.RemoveQuebraJatoEvent;
import dr.model.QuebraJato;
import dr.ui.Dialog;
import dr.ui.quebraJato.IncluirQuebraJatoView;
import dr.validation.QuebraJatoValidator;

import dr.validation.Validator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a
 * <code>Controller</code> responsável por gerir a tela de inclusão/edição de
 * <code>QuebraJato</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @Andre
 */
public class IncluirQuebraJatoController extends PersistenceController {

    private IncluirQuebraJatoView view;
    private Validator<QuebraJato> validador = new QuebraJatoValidator();

    public IncluirQuebraJatoController(AbstractController parent) {
        super(parent);

        this.view = new IncluirQuebraJatoView();
        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                IncluirQuebraJatoController.this.cleanUp();
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

        registerAction(this.view.getSaveButton(),
                ConditionalAction.build()
                .addConditional(new BooleanExpression() {
            @Override
            public boolean conditional() {
                QuebraJato b = view.getQuebraJato();
                String msg = validador.validate(b);
                if (!"".equals(msg == null ? "" : msg)) {
                    Dialog.showInfo("Validacão", msg, view);
                    return false;
                }

                return true;
            }
        })
                .addAction(
                TransactionalAction.build()
                .persistenceCtxOwner(IncluirQuebraJatoController.this)
                .addAction(new AbstractAction() {
            private QuebraJato e;

            @Override
            protected void action() {
                e = view.getQuebraJato();
                QuebraJatoDAO dao = new QuebraJatoDAOImpl(getPersistenceContext());
                try {
                    e = dao.save(e);
                } catch (Exception ex) {
                    Logger.getLogger(IncluirQuebraJatoController.class.getName()).log(Level.SEVERE, null, ex);
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
                fireEvent(new IncluirQuebraJatoEvent(e));
            }
        })));

        registerAction(view.getRemoveButton(),
                ConditionalAction.build()
                .addConditional(new BooleanExpression() {
            @Override
            public boolean conditional() {

//                Dialog.buildConfirmation("Confirmação", "Deseja remover este QuebraJato e todas as coletas relacionadas?", view).addYesButton(new EventHandler() {
//                    @Override
//                    public void handle(Event t) {
//                        dialog = true;
//                    }
//                }).addNoButton(new EventHandler() {
//                    @Override
//                    public void handle(Event t) {
//                        dialog = false;
//                    }
//                }).build()
//                        .show();


                return true;
            }
        })
                //                .addAction(
                //                TransactionalAction.build()
                //                .persistenceCtxOwner(IncluirQuebraJatoController.this)
                .addAction(
                new AbstractAction() {
            private QuebraJato e;

            @Override
            protected void action() {

//
//                if (dialog!= null && dialog) {
                Integer id = view.getQuebraJatoId();
                if (id != null) {
                    try {

                        QuebraJatoDAO dao = new QuebraJatoDAOImpl(getPersistenceContext());
                        e = dao.findById(id);
                        if (e != null) {
                            dao.remove(e);
//                                dialog = false;

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(IncluirQuebraJatoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
//            }

            @Override
            public void posAction() {
                view.hide();
                fireEvent(new RemoveQuebraJatoEvent(e));
            }
        }));
    }

    public void show() {
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }

    public void show(QuebraJato e) {
        view.setQuebraJato(e);
        view.setTitle("Editar QuebraJato");
        show();
    }

    @Override
    protected void cleanUp() {
        view.setTitle("Incluir QuebraJato");
        view.resetForm();

        super.cleanUp();
    }
}
