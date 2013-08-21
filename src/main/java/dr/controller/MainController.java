package dr.controller;

import dr.action.AbstractAction;
import dr.ui.MainView;
import dr.ui.SobreView;
import dr.util.JPAUtil;
import javafx.stage.Stage;

/**
 * Define a <code>Controller</code> principal do sistema, responsável por gerir a tela com a lista de <code>Ensaio</code>.
 * 
 * @see controller.PersistenceController
 * 
 * @author @andre
 */
public class MainController extends PersistenceController {

    private IncluirEnsaioController addEnsaioController;
    private ListaEnsaioController ensaioListController;
    private MainView mainView;

    public MainController(final Stage mainStage) {
        loadPersistenceContext();
        this.mainView = new MainView(mainStage);
        this.addEnsaioController = new IncluirEnsaioController(this);
        this.ensaioListController = new ListaEnsaioController(this);
        
        final SobreView sobreView = new SobreView();
        
        registerAction(mainView.getMenuEnsaio(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.addEnsaioController.show();
            }
        });
        
        registerAction(mainView.getMenuEnsaioList(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.ensaioListController.show();
            }
        });
        registerAction(mainView.getMenuAbout(), new AbstractAction() {
            @Override
            public void action() {
                sobreView.show();
            }
        });

    }
    
    @Override
    public void cleanUp() {
        super.cleanUp();
        JPAUtil.closeEntityManagerFactory();
    }
}
