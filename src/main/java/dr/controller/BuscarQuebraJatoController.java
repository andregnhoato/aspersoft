package dr.controller;

import dr.action.AbstractAction;
import dr.action.BooleanExpression;
import dr.action.ConditionalAction;
import dr.dao.QuebraJatoDAO;
import dr.dao.QuebraJatoDAOImpl;
import dr.event.BuscarQuebraJatoEvent;
import dr.model.QuebraJato;
import dr.ui.quebraJato.BuscarQuebraJatoView;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 * Define a
 * <code>Controller</code> responsável por gerir a tela de Busca de
 * <code>QuebraJato</code> pelo campo
 * <code>nome</code>.
 *
 * @see controller.PersistenceController
 *
 * @author
 * @Andre
 */
public class BuscarQuebraJatoController extends PersistenceController {

    private BuscarQuebraJatoView view;

    public BuscarQuebraJatoController(ListaQuebraJatoController parent) {
        super(parent);
        this.view = new BuscarQuebraJatoView();

        this.view.addEventHandler(WindowEvent.WINDOW_HIDDEN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent window) {
                BuscarQuebraJatoController.this.cleanUp();
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
            private List<QuebraJato> list;

            @Override
            protected void action() {

                QuebraJatoDAO dao = new QuebraJatoDAOImpl(getPersistenceContext());
                
                list = dao.getQuebraJatoByDescricao(view.getText());
            }

            @Override
            public void posAction() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        view.hide();
                    }
                });
                fireEvent(new BuscarQuebraJatoEvent(list));
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
