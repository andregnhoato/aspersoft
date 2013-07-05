package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.action.TransactionalAction;
import dr.dao.EnsaioDAO;
import dr.dao.EnsaioDAOJPA;
import dr.event.ensaio.IncluirEnsaioEvent;
import dr.event.ensaio.RemoveEnsaioEvent;
import dr.model.Ensaio;
import dr.ui.Dialog;
import dr.ui.ensaio.IncluirEnsaioView;
import dr.validation.EnsaioValidator;
import dr.validation.Validator;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de inclusão/edição de <code>Ensaio</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @Andre
 */
public class IncluirEnsaioController extends PersistenceController {
    
    private IncluirEnsaioView view;
    private Validator<Ensaio> validador = new EnsaioValidator();
    
    public IncluirEnsaioController(AbstractController parent) {
        super(parent);
        
        this.view = new IncluirEnsaioView();
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
                                    EnsaioDAO dao = new EnsaioDAOJPA(getPersistenceContext());
                                    e = dao.save(e);
                                }

                                @Override
                                protected void posAction() {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            view.hide();
                                        }
                                    });
                                    fireEvent(new IncluirEnsaioEvent(e));
                                }
                            })));
        
        registerAction(view.getRemoveButton(), 
                TransactionalAction.build()
                    .persistenceCtxOwner(IncluirEnsaioController.this)
                    .addAction(new AbstractAction() {
                        private Ensaio e;
                        
                        @Override
                        protected void action() {
                            Integer id = view.getEnsaioId();
                            if (id != null) {
                                EnsaioDAO dao = new EnsaioDAOJPA(getPersistenceContext());
                                e = dao.findById(id);
                                if (e != null) { 
                                    dao.remove(e);
                                }
                            }
                        }
                        
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
        
        super.cleanUp();
    }
}