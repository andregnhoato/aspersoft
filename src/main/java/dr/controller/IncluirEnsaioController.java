package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.action.TransactionalAction;
import dr.dao.ColetaDAO;
import dr.dao.ColetaDAOImpl;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOImpl;
import dr.event.IncluirEnsaioEvent;
import dr.event.RemoveEnsaioEvent;
import dr.model.Coleta;
import dr.model.Ensaio;
import dr.ui.Dialog;
import dr.ui.coleta.ColetaTable;
import dr.ui.ensaio.IncluirEnsaioView;
import dr.validation.EnsaioValidator;
import dr.validation.Validator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a
 * <code>Controller</code> responsável por gerir a tela de inclusão/edição de
 * <code>Ensaio</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @Andre
 */
public class IncluirEnsaioController extends PersistenceController {

    private IncluirEnsaioView view;
    private Validator<Ensaio> validador = new EnsaioValidator();
    static Boolean dialog;
    private ListaBocalController bocalListController;
    private ListaQuebraJatoController quebraListController;

    public IncluirEnsaioController(AbstractController parent) {
        super(parent);

        this.view = new IncluirEnsaioView();
        this.bocalListController = new ListaBocalController(this);
        this.quebraListController = new ListaQuebraJatoController(this);


        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                IncluirEnsaioController.this.cleanUp();
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
                Ensaio e = view.getEnsaio();
                String msg = validador.validate(e);
                if (!"".equals(msg == null ? "" : msg)) {
                    Dialog.showInfo("Validacão", msg, view);
                    return false;
                }
                if (e.getEspacamentoPluviometro() <= 0) {
                    Dialog.showError("Validação", "Necessário informar uma valor superior a zero para o campo Espaço entre pluviometros");
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
                .persistenceCtxOwner(IncluirEnsaioController.this)
                .addAction(new AbstractAction() {
            private Ensaio e;

            @Override
            protected void action() {
                e = view.getEnsaio();
                EnsaioDAO dao = new EnsaioDAOImpl(getPersistenceContext());
                try {
                    e = dao.save(e);
                } catch (Exception ex) {
                    Logger.getLogger(IncluirEnsaioController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            protected void posAction() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.hide();
                        try {
                            ColetaDAO dao = new ColetaDAOImpl(getPersistenceContext());
                            try {
                                if (dao.findColetasByEnsaio(e).isEmpty()) {
                                    for (int linha = 0; linha < (e.getGridAltura() / e.getEspacamentoPluviometro()); linha++) {
                                        for (int coluna = 0; coluna < (e.getGridLargura() / e.getEspacamentoPluviometro()); coluna++) {
                                            Coleta c = new Coleta();
                                            c.setColuna(coluna);
                                            c.setLinha(linha);
                                            c.setEnsaio(e);
                                            c.setValor(0F);
                                            try {
                                                dao.save(c);
                                            } catch (Exception ex) {
                                                Logger.getLogger(ColetaTable.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.err.println(e.getMessage());
                            }

                        } catch (Exception ex) {
                            System.err.println(ex.getMessage());
                        }

                    }
                });
                fireEvent(new IncluirEnsaioEvent(e));
            }
        })));

        registerAction(view.getRemoveButton(),
                ConditionalAction.build()
                .addConditional(new BooleanExpression() {
            @Override
            public boolean conditional() {

//                Dialog.buildConfirmation("Confirmação", "Deseja remover este Ensaio e todas as coletas relacionadas?", view).addYesButton(new EventHandler() {
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
                //                .persistenceCtxOwner(IncluirEnsaioController.this)
                .addAction(
                new AbstractAction() {
            private Ensaio e;

            @Override
            protected void action() {

//
//                if (dialog!= null && dialog) {
                Integer id = view.getEnsaioId();
                if (id != null) {
                    try {

                        EnsaioDAO dao = new EnsaioDAOImpl(getPersistenceContext());
                        e = dao.findById(id);
                        if (e != null) {
                            dao.remove(e);
//                                dialog = false;

                        }
                    } catch (Exception ex) {
                        Logger.getLogger(IncluirEnsaioController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
//            }

            @Override
            public void posAction() {
                view.hide();
                fireEvent(new RemoveEnsaioEvent(e));
            }
        }));
    }

    public void show() {
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }

    public void show(Ensaio e) {
        view.setEnsaio(e);
        view.setTitle("Editar Ensaio");
        show();
    }

    @Override
    protected void cleanUp() {
        view.setTitle("Incluir Ensaio");
        view.resetForm();

//        super.cleanUp();
    }
}
