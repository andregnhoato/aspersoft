package dr.dao;

import dr.model.Coleta;
import dr.model.Ensaio;
import java.util.List;

/**
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author @Andre
 */
public interface ColetaDAO extends AbstractDAO<Coleta>{

    /*
     * @param nome Filtro da pesquisa de coletas.
     * @return Lista de coletas com filtro em nome.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Coleta> findColetasByEnsaio(Ensaio e)throws Exception;
    Coleta findColetaByPosicao(Ensaio e, int linha, int coluna)throws Exception;

   
}
