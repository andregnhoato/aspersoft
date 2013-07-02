package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.action.TransactionalAction;
import dr.dao.ExperimentDAO;
import dr.dao.ExperimentDAOJPA;
import dr.event.experiment.AddExperimentEvent;
import dr.event.experiment.RemoveExperimentEvent;
import dr.model.Experiment;
import dr.event.experiment.AddExperimentView;
import dr.ui.Dialog;
import dr.validation.ExperimentValidator;
import dr.validation.Validator;
import java.sql.Date;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de inclusão/edição de <code>Experiment</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @Andre
 */
public class AddExperimentController extends PersistenceController {
    
    private AddExperimentView view;
    private Validator<Experiment> validador = new ExperimentValidator();
    
    public AddExperimentController(AbstractController parent) {
        super(parent);
        
        this.view = new AddExperimentView();
        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                AddExperimentController.this.cleanUp();
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
                            Experiment e = view.getExperiment();
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
                            .persistenceCtxOwner(AddExperimentController.this)
                            .addAction(new AbstractAction() {
                                private Experiment e;

                                @Override
                                protected void action() {
                                    e = view.getExperiment();
                                    ExperimentDAO dao = new ExperimentDAOJPA(getPersistenceContext());
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
                                    fireEvent(new AddExperimentEvent(e));
                                }
                            })));
        
        registerAction(view.getRemoveButton(), 
                TransactionalAction.build()
                    .persistenceCtxOwner(AddExperimentController.this)
                    .addAction(new AbstractAction() {
                        private Experiment e;
                        
                        @Override
                        protected void action() {
                            Integer id = view.getExperimentId();
                            if (id != null) {
                                ExperimentDAO dao = new ExperimentDAOJPA(getPersistenceContext());
                                e = dao.findById(id);
                                if (e != null) { 
                                    dao.remove(e);
                                }
                            }
                        }
                        
                        @Override
                        public void posAction() {
                            view.hide();
                            fireEvent(new RemoveExperimentEvent(e));
                        }
                    }));
    }
    
    public void show() {
        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        view.show();
    }
    
    public void show(Experiment e) {
        view.setExperiment(e);
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
