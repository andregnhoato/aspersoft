package dr.controller;

import dr.action.AbstractAction;
import dr.event.AbstractEvent;
import dr.event.AbstractEventListener;
import dr.ui.Dialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.MenuItem;
import org.apache.log4j.Logger;

/**
 * Classe abstrata que define uma estrutura para componentes da camada controller do padrão arquitetural MVC.
 * 
 * <p><code>Controller</code> é o componente intermediário entre a apresentação (View) e os componentes de negócio (Serviços + DAO + Model).</p>
 * 
 * <p>Habilita:</p>
 * <ul>
 *   <li>Definição de <code>eventos</code> e <code>ações</code> para os componentes gráficos.</li>
 *   <li>Apresentar mensagens de erros gerados em <code>ações</code>dos componentes gráficos.</li>
 *   <li>Liberar recursos do componente no encerramento da janela.</li>
 * </ul>
 * 
 * @author @Andre
 */
public class AbstractController implements EventHandler<ActionEvent> {

    private static Logger log = Logger.getLogger(AbstractController.class);
    
    private AbstractController parent;
    private java.util.List<AbstractController> subControllers = new ArrayList<>();
    private Map<String, AbstractAction> actions = new HashMap<>();
    private Map<Class<?>, List<AbstractEventListener>> eventListeners = new HashMap<>();

    public AbstractController() {
    }

    /**
     * Controller possui um auto-relacionamento, útil em situações aonde uma
     * hierarquia de controladores deve ser respeitada.
     *
     * @param parent controller <i>pai</i>
     */
    public AbstractController(AbstractController parent) {
        if (parent != null) {
            this.parent = parent;
            this.parent.subControllers.add(this);
        }
    }

    /**
     * Vincula uma <code>ação</code> ao <code>id</code> do componente <code>Button</code>.
     * 
     * @param source <code>Button</code> que deve executar a <code>ação</code>
     * @param action código com a <code>ação</code> que deve ser executada.
     * 
     * @throws <code>RuntimeException</code> se o <code>Button</code> não tem <code>id</code>.
     */
    protected void registerAction(ButtonBase source, AbstractAction action) {
        log.debug("Registrando action: " + action.getClass().getName() + " para o botão: " + source.getText());
        registerAction(source.getId(), action);
        source.setOnAction(this);
    }
    
    /**
     * Vincula uma <code>ação</code> ao <code>id</code> do componente <code>MenuItem</code>.
     * @param source <code>MenuItem</code> que deve executar a <code>ação</code>
     * @param action código com a <code>ação</code> que deve ser executada.
     * @throws <code>RuntimeException</code> se o <code>MenuItem</code> não tem <code>id</code>.
     */
    protected void registerAction(MenuItem source, AbstractAction action) {
        log.debug("Registrando action: " + action.getClass().getName() + " para o menu: " + source.getText());
        registerAction(source.getId(), action);
        source.setOnAction(this);
    }
    
    private void registerAction(String id, AbstractAction action) {
        if (id == null) {
            throw new RuntimeException("Componente sem ação definida!");
        }
        this.actions.put(id, action);
    }

    /**
     * Notifica o <code>evento</code>.
     *
     * @param event tipo do evento
     */
    public void fireEvent(AbstractEvent<?> event) {
        if (eventListeners.get(event.getClass()) != null) {
            for (AbstractEventListener eventListener : eventListeners.get(event.getClass())) {
                log.debug("Evento: " + event.getClass().getName() + " com listener: " + eventListener.getClass().getName());
                eventListener.handleEvent(event);
            }
        }
        if (parent != null) {
            parent.fireEvent(event);
        }
    }

    /**
     * Registra um <code>listener</code> que deve ser acionado de acordo com 
     * o tipo do <code>evento</code>.
     *
     * @param eventClass tipo do evento
     * @param eventListener tratador (listener) do evento
     */
    protected void registerEventListener(Class<?> eventClass, AbstractEventListener eventListener) {
        log.debug("Registrando listener: " + eventListener + " para o evento: " + eventClass.getName());
        java.util.List<AbstractEventListener> listenersForEvent = eventListeners.get(eventClass);
        if (listenersForEvent == null) {
            listenersForEvent = new ArrayList<AbstractEventListener>();
        }
        listenersForEvent.add(eventListener);
        eventListeners.put(eventClass, listenersForEvent);
    }
    
    /**
     * @param evt uilizado para acessar o componente que disparou a <code>ação</code>.
     * @return qual é a instância de <code>AbstractAction</code> vinculada ao componente que disparou a <code>ação</code>.
     * @throws <code>RuntimeException</code> caso não encontre o componente origem.
     */
    protected AbstractAction getAction(ActionEvent evt) {
        String actionCommand = null;
        if (evt.getSource() instanceof ButtonBase) {
            ButtonBase button = (ButtonBase) evt.getSource();
            actionCommand = button.getId();
        }
        
        if (actionCommand == null) {
            if (evt.getSource() instanceof MenuItem) {
                MenuItem menu = (MenuItem) evt.getSource();
                actionCommand = menu.getId();
            }
        }
        
        if (actionCommand == null) {
            log.error("Gerador do evento desconhecido, nao eh Button ou MenuItem!");
            throw new RuntimeException("Gerador do evento desconhecido, nao eh Button ou MenuItem!");
        }
        
        return actions.get(actionCommand);
    }

    @Override
    public void handle(ActionEvent evt) {
        try {
            AbstractAction action = getAction(evt);
            if (action != null) {
                log.debug("Executando a action: " + action.getClass());
                action.actionPerformed();
            }
        } catch (Exception ex) {
            handlerException(ex);
        }
    }

    /**
     * Caso ocorra alguma falha durante a <code>ação</code> apresenta uma mensagem.
     *
     * @param ex
     */
    protected void handlerException(Exception ex) {
        log.error(ex);
        Dialog.showThrowable("Erro", ex.getMessage(), ex);
    }

    public AbstractController getParentController() {
        return parent;
    }

    /**
     * Método utilizado para liberar recursos carregados pela Controller.
     */
    protected void cleanUp() {
        log.debug("Liberando recursos do controller " + this.getClass().getName());

        for (AbstractController subController : subControllers) {
            subController.cleanUp();
        }
    }
    
}
