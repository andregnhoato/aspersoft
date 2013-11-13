package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.action.TransactionalAction;
import dr.dao.BocalDAO;
import dr.dao.BocalDAOImpl;
import dr.event.IncluirBocalEvent;
import dr.event.RemoveBocalEvent;
import dr.model.Bocal;
import dr.ui.Dialog;
import dr.ui.bocal.IncluirBocalView;
import dr.validation.BocalValidator;

import dr.validation.Validator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a
 * <code>Controller</code> responsável por gerir a tela de inclusão/edição de
 * <code>Bocal</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @Andre
 */
public class IncluirBocalController extends PersistenceController {

    private IncluirBocalView view;
    private Validator<Bocal> validador = new BocalValidator();

    public IncluirBocalController(AbstractController parent) {
        super(parent);

        this.view = new IncluirBocalView();
        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                IncluirBocalController.this.cleanUp();
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
                Bocal b = view.getBocal();
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
                .persistenceCtxOwner(IncluirBocalController.this)
                .addAction(new AbstractAction() {
            private Bocal e;

            @Override
            protected void action() {
                e = view.getBocal();
                BocalDAO dao = new BocalDAOImpl(getPersistenceContext());
                try {
                    e = dao.save(e);
                } catch (Exception ex) {
                    Logger.getLogger(IncluirBocalController.class.getName()).log(Level.SEVERE, null, ex);
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
                fireEvent(new IncluirBocalEvent(e));
            }
        })));

        registerAction(view.getRemoveButton(),
                ConditionalAction.build()
                .addConditional(new BooleanExpression() {
            @Override
            public boolean conditional() {

//                Dialog.buildConfirmation("Confirmação", "Deseja remover este Bocal e todas as coletas relacionadas?", view).addYesButton(new EventHandler() {
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
                //                .persistenceCtxOwner(IncluirBocalController.this)
                .addAction(
                new AbstractAction() {
            private Bocal e;

            @Override
            protected void action() {

//
//                if (dialog!= null && dialog) {
                Integer id = view.getBocalId();
                if (id != null) {
                    try {

                        BocalDAO dao = new BocalDAOImpl(getPersistenceContext());
                        e = dao.findById(id);
                        if (e != null) {
                            dao.remove(e);
//                                dialog = false;

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(IncluirBocalController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
//            }

            @Override
            public void posAction() {
                view.hide();
                fireEvent(new RemoveBocalEvent(e));
            }
        }));
    }

    public void show() {
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }

    public void show(Bocal e) {
        view.setBocal(e);
        view.setTitle("Editar Bocal");
        show();
    }

    @Override
    protected void cleanUp() {
        view.setTitle("Incluir Bocal");
        view.resetForm();

        super.cleanUp();
    }
}
