package dr.model;

/**
 * Estipula um contrato base para as entidades persistentes da aplicação.
 *
 * <p>Esse contrato é utilizado pelo componente base de persistência:
 * <code>AbstractDAO</code>.</p>
 *
 * @see dao.AbstractDAO
 *
 * @author @Andre
 */
public interface Bean {

    /**
     * @return A referência para a chave primária (Primary Key) de cada objeto
     * persistido. Caso o objeto ainda não tenha sido persistido, deve
     * retornar <code>null</code>.
     */
    public Number getId();
}
