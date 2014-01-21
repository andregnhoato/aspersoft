package dr.controller;

import dr.action.AbstractAction;
import dr.dao.ConfiguracaoDAO;
import dr.dao.ConfiguracaoDAOImpl;
import dr.model.Configuracao;
import dr.ui.configuracao.ConfiguracaoView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a
 * <code>Controller</code> responsável por gerir a tela de inclusão/edição de
 * <code>Configuracao</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @Andre
 */
public class ConfiguracaoController extends PersistenceController {

    private ConfiguracaoView view;
    Configuracao c;
    ConfiguracaoDAO dao;

    public ConfiguracaoController(AbstractController parent) {
        super(parent);

        loadPersistenceContext(((PersistenceController) getParentController()).getPersistenceContext());
        dao = new ConfiguracaoDAOImpl(getPersistenceContext());
        
        this.view = new ConfiguracaoView();
        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                ConfiguracaoController.this.cleanUp();
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
                new AbstractAction() {
            private Configuracao e;

            @Override
            protected void action() {
                e = view.getConfiguracao();
                try {
                    
                    e = dao.save(e);
                } catch (Exception ex) {
                    Logger.getLogger(ConfiguracaoController.class.getName()).log(Level.SEVERE, null, ex);
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
            }
        });
        
    }

    public void show() {
        try {
            view.setConfiguracao(dao.findById(1));
        } catch (Exception ex) {
            Logger.getLogger(ConfiguracaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        view.show();
    }

    public void show(Configuracao e) {
        view.setConfiguracao(e);
        show();
    }

    @Override
    protected void cleanUp() {
        view.resetForm();

        super.cleanUp();
    }
}
