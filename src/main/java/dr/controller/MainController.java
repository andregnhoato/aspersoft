package dr.controller;

import dr.action.AbstractAction;
import dr.ui.MainView;
import dr.ui.SobreView;
import dr.ui.ajuda.AjudaView;
import dr.util.JPAUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

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
public class MainController extends PersistenceController {

    private IncluirEnsaioController addEnsaioController;
    private ListaEnsaioController ensaioListController;
    private MainView mainView;
    private ConfiguracaoController configController;
    private ListaBocalController bocalListController;
    private ListaQuebraJatoController quebraJatoListController;
    private ListaCombinacaoController combinacaoController;
    private PerfilController perfilController;

    public MainController(final Stage mainStage) {
        loadPersistenceContext();
        this.mainView = new MainView(mainStage);
        this.addEnsaioController = new IncluirEnsaioController(this);
        this.ensaioListController = new ListaEnsaioController(this);
        this.configController = new ConfiguracaoController(this);
        this.bocalListController = new ListaBocalController(this);
        this.quebraJatoListController = new ListaQuebraJatoController(this);
        this.combinacaoController = new ListaCombinacaoController(this);
        this.perfilController = new PerfilController(this);

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

        registerAction(mainView.getMenuConfiguracao(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.configController.show();

            }
        });

        registerAction(mainView.getMenuBocais(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.bocalListController.show();

            }
        });

        registerAction(mainView.getMenuQuebraJato(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.quebraJatoListController.show();

            }
        });

        registerAction(mainView.getMenuCombinacao(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.combinacaoController.show();

            }
        });

        registerAction(mainView.getMenuPerfil(), new AbstractAction() {
            @Override
            protected void action() {
                MainController.this.perfilController.show();

            }
        });

        registerAction(mainView.getMenuAjuda(), new AbstractAction() {

            @Override
            protected void action() {
                try {
                    AjudaView ajuda = new AjudaView();
                    ajuda.show();
                    
                } catch (Exception ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        

    }

    @Override
    public void cleanUp() {
//        super.cleanUp();
        JPAUtil.closeEntityManagerFactory();
    }
}
