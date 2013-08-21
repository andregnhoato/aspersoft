package dr.action;

import org.apache.log4j.Logger;

/**
 * Componente representa uma ação, normalmente vinculada a intervenção do usuário nos componentes de interface gráfica para solicitar uma operação no sistema.
 * 
 * <p>Nessa aplicação a <code>AbstractAction</code> define um tipo de componente complementar ao <code>MVC</code> (<strong>M</strong>odel <strong>V</strong>iew <strong>C</strong>ontroller), um modelo arquitetural utilizado para organizar os componentes do sistema.</p>
 * <p>Utiliza o design pattern <code>Template Method</code> para definir um estrutura/template com pré e pós processamento vinculados a ação:
 * 
 * <ul>
 *   <li><code>preAction()</code>: Acionando antes da execução de <code>action()</code>.</li>
 *   <li><code>posAction()</code>: Acionando depois da execução (com sucesso) de <code>action()</code>.</li>
 *   <li><code>actionFailure()</code>: Acionando caso a execução de <code>action()</code> falhe.</li>
 * </ul>
 * 
 * @author @Andre
 */
public abstract class AbstractAction {

    private static Logger log = Logger.getLogger(AbstractAction.class);
    
    /**
     * Método principal, que define o processamento da <code>Action</code>.
     */
    protected abstract void action();

    /**
     * Método acionado <strong>antes</string> de <code>action()</code>
     * <p>
     *  Caso uma exceção (<code>RuntimeException</code>) seja lançada, a execução de
     *  toda a <code>AbstractAction</code> é interrompida.
     * </p>
     */
    protected void preAction() {
    }

    /**
     * Método executado após a conclusão de <code>action()</code>.
     */
    protected void posAction() {
    }

    /**
     * Método é acionado quando alguma falha ocorre durante a execução de
     * <code>preAction</code>, <code>action</code> ou <code>posAction</code>.
     */
    protected void actionFailure() {
    }

    /**
     * Método responsável por organizar e executar a cadeia de métodos de <code>AbstractAction</code>.
     * @throws <code>RuntimeException</code> caso algum erro ocorra.
     */
    public void actionPerformed() {
        try {
            preAction();
            action();
            posAction();
        } catch (Exception ex) {
            actionFailure();
            log.error("Falha ao executar action: "+ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
