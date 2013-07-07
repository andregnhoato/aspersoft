package dr.action;

import dr.controller.PersistenceController;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Adiciona o controle transacional a uma ação.
 * 
 * <p>
 *  <code>TransactionalAction</code> possui uma referência para outra <code>AbstractAction</code>, quem efetivamente implementa a ação.
 *  Realiza o controle da transação utilizando a API <code>JPA</code>.
 * </p>
 * 
 * <p>
 *  O escopo transacional está disponível somente para o método <code>action()</code>.
 *  As operações <code>preAction</code>, <code>posAction</code> e <code>actionFailure</code> são delegadas para a ação contida.
 * </p>
 * 
 * <p>Para implementar esse componente utilizamos o padrão de projeto <strong>Decorator</strong>.</p>
 * 
 * @author @Andre
 */
public class TransactionalAction extends AbstractAction {

    /**
     * Referência para a ação que deverá executar em escopo transacional.
     */
    private AbstractAction action;
    /**
     * Referência para o controlador do gerenciador de persistência.
     */
    private PersistenceController persistenceController;

    private TransactionalAction() {
    }

    /**
     * Executa o método
     * <code>action</code> da ação encapsulada com escopo transacional.
     *
     * @throws <code>IllegalArgumentException</code> caso não tenha ação,
     * <code>persistenceController</code> e/ou <code>EntityManager</code>
     * vinculado.
     */
    @Override
    protected void action() {
        if (action == null) {
            throw new IllegalArgumentException("Indique a Ação que deve ser executada.");
        }

        if (persistenceController == null) {
            throw new IllegalArgumentException("Informe o dono do contexto de persistencia.");
        }

        EntityManager em = persistenceController.getPersistenceContext();
        if (em == null) {
            throw new IllegalArgumentException("Sem referencia para o gerenciador de persistencia.");
        }

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.action();
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(ex);
        }
    }

    /**
     * Delega a execução para o método
     * <code>preAction</code> ação encapsulada.
     */
    @Override
    protected void preAction() {
        if (action != null) {
            action.preAction();
        }
    }

    /**
     * Delega a execução para o método
     * <code>posAction</code> ação encapsulada.
     */
    @Override
    protected void posAction() {
        if (action != null) {
            action.posAction();
        }
    }

    /**
     * Delega a execução para o método
     * <code>actionFailure</code> ação encapsulada.
     */
    @Override
    protected void actionFailure() {
        if (action != null) {
            action.actionFailure();
        }
    }

    /**
     * @return Constrói e retorna uma instância
     * de <code>TransactionalAction</code> sem ação e condição definida.
     */
    public static TransactionalAction build() {
        return new TransactionalAction();
    }

    /**
     * Adiciona uma ação ao
     * <code>TransactionalAction</code>.
     *
     * @param action ação que efetivamente executa as
     * operações <code>TransactionalAction</code>.
     * @return <code>TransactionalAction</code> com uma ação definida.
     */
    public TransactionalAction addAction(AbstractAction action) {
        this.action = action;
        return this;
    }

    /**
     * Passa a referência para o
     * <code>PersistenceController</code> <i>dono</i> do gerenciador de
     * persistência. <p>O acesso ao
     * <code>EntityManager</code> ocorre via
     * <code>persistenceController</code>.</p>
     *
     * @param persistenceController referência para o persistence controller.
     * @return <code>TransactionalAction</code>
     * com <code>persistenceController</code> vinculado.
     */
    public TransactionalAction persistenceCtxOwner(PersistenceController persistenceController) {
        this.persistenceController = persistenceController;
        return this;
    }
}
