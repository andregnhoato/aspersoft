package dr.dao;

import dr.model.Ensaio;
import java.util.List;

/**
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author @Andre
 */
public interface EnsaioDAO extends AbstractDAO<Ensaio>{

   
    /**
     * @param nome Filtro da pesquisa de ensaios.
     * @return Lista de ensaios com filtro em nome.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Ensaio> getEnsaiosByDescricao(String Descricao);
}
