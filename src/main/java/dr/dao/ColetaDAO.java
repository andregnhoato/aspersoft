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
public interface ColetaDAO {

    /**
     * Faz a inserção ou atualização
     *
     * @param coleta
     * @return referência atualizada do objeto.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    Coleta save(Coleta coleta);

    /**
     * Exclui o registro da coleta na base de dados
     *
     * @param coleta
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    void remove(Coleta coleta);

    /**
     * @return Lista com todas as coletas cadastradas no banco de dados.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Coleta> getAll();

    /**
     * @param nome Filtro da pesquisa de coletas.
     * @return Lista de coletas com filtro em nome.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Coleta> findColetasByEnsaio(Ensaio e);

    /**
     * @param id filtro da pesquisa.
     * @return Experimet com filtro no id, caso nao exista
     * retorna <code>null</code>.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    Coleta findById(Integer id);
}
