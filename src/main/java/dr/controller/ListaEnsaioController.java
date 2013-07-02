package dr.controller;

import dr.action.AbstractAction;
import dr.dao.ExperimentDAO;
import dr.dao.ExperimentDAOJPA;
import dr.event.AbstractEventListener;
import dr.event.experiment.AddExperimentEvent;
import dr.event.experiment.RemoveExperimentEvent;
import dr.event.experiment.UpdateListExperimentEvent;
import dr.model.Experiment;
import dr.ui.experiment.ExperimentListView;
import dr.util.JPAUtil;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Mercadoria</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @andre
 */
public class ExperimentListController extends PersistenceController {

    private ExperimentListView view;
    private AddExperimentController addExperimentController;
    //private BuscarMercadoriaController buscarController;

    public ExperimentListController(AbstractController parent) {
        super(parent);
        loadPersistenceContext();
        this.view = new ExperimentListView();
        this.addExperimentController = new AddExperimentController(this);
        //this.buscarController = new BuscarMercadoriaController(this);
        
        registerAction(view.getNewButton(), new AbstractAction() {
            @Override
            protected void action() {
                ExperimentListController.this.addExperimentController.show();
            }
        });
        
        /*registerAction(view.getFindButton(), new AbstractAction() {
            @Override
            protected void action() {
                ExperimentListController.this.buscarController.show();
            }
        });*/
        
        registerAction(view.getRefreshButton(), new AbstractAction() {
            @Override
            protected void action() {
                refreshTable();
            }
        });
        
        view.getTable().setMouseEvent(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                if (t.getClickCount() == 2) {
                    Experiment e = view.getTable().getExperimentSelected();
                    if (e != null) {
                        ExperimentListController.this.addExperimentController.show(e);
                    }
                }
            }
        });
        
        registerEventListener(AddExperimentEvent.class, new AbstractEventListener<AddExperimentEvent>() {
            @Override
            public void handleEvent(AddExperimentEvent event) {
                refreshTable();
            }
        });
        
        registerEventListener(RemoveExperimentEvent.class, new AbstractEventListener<RemoveExperimentEvent>() {
            @Override
            public void handleEvent(RemoveExperimentEvent event) {
                refreshTable();
            }
        });
        
        registerEventListener(UpdateListExperimentEvent.class, new AbstractEventListener<UpdateListExperimentEvent>() {
            @Override
            public void handleEvent(UpdateListExperimentEvent event) {
                refreshTable();
            }
        });
        
        /*registerEventListener(BuscarMercadoriaEvent.class, new AbstractEventListener<BuscarMercadoriaEvent>() {
            @Override
            public void handleEvent(BuscarMercadoriaEvent event) {
                List<Mercadoria> list = event.getTarget();
                if (list != null) {
                    refreshTable(event.getTarget());
                }
            }
        }); */
        
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
    
    private void refreshTable(List<Experiment> list) {
        //view.addTransition();
        if (list != null) {
            view.refreshTable(list);
            return;
        }
        
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                ExperimentDAO dao = new ExperimentDAOJPA(getPersistenceContext());
                view.refreshTable(dao.getAll());
            }
        });
    }
}
