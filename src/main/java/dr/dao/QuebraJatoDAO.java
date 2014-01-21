package dr.dao;

import dr.model.QuebraJato;
import java.util.List;

/**
 *
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern
 * <code>Data Access Object</code>.</p>
 *
 * @author
 * @Andre
 */
public interface QuebraJatoDAO extends AbstractDAO<QuebraJato> {

    public List<QuebraJato> getQuebraJatoByDescricao(String descricao);
    
}
