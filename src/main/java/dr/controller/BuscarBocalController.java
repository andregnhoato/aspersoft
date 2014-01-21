package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.dao.BocalDAO;
import dr.dao.BocalDAOImpl;
import dr.event.BuscarBocalEvent;
import dr.model.Bocal;
import dr.ui.bocal.BuscarBocalView;
import dr.util.DateUtil;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a
 * <code>Controller</code> responsável por gerir a tela de Busca de
 * <code>Bocal</code> pelo campo
 * <code>nome</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @Andre
 */
public class BuscarBocalController extends PersistenceController {

    private BuscarBocalView view;

    public BuscarBocalController(ListaBocalController parent) {
        super(parent);
        this.view = new BuscarBocalView();

        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                BuscarBocalController.this.cleanUp();
            }
        });

        registerAction(this.view.getCancelarButton(), new AbstractAction() {
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

        registerAction(view.getBuscarButton(), ConditionalAction.build()
                .addConditional(new BooleanExpression() {
            @Override
            public boolean conditional() {

                return view.getText().length() > 0;
            }
        })
                .addAction(new AbstractAction() {
            private List<Bocal> list;

            @Override
            protected void action() {

                BocalDAO dao = new BocalDAOImpl(getPersistenceContext());
                
                list = dao.getBocalByDescricao(view.getText());
            }

            @Override
            public void posAction() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.hide();
                    }
                });
                fireEvent(new BuscarBocalEvent(list));
                list = null;
            }
        }));
    }

    public void show() {
        loadPersistenceContext();
        view.show();
    }

    @Override
    protected void cleanUp() {
        view.resetForm();
//	super.cleanUp();
    }
}
