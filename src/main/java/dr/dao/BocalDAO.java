package dr.dao;

import dr.model.Bocal;
import dr.model.Ensaio;
import java.util.List;

/**
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author @Andre
 */
public interface BocalDAO extends AbstractDAO<Bocal>{

    List<Bocal> getBocalByDescricao(String descricao);
}
