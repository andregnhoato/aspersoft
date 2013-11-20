package dr.dao;

import dr.model.Combinacao;
import java.util.List;

/**
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author @Andre
 */
public interface CombinacaoDAO extends AbstractDAO<Combinacao>{
    
    public List<Combinacao> getCombinacaoByDescricao(String descricao);

}
