package dr.dao;

import dr.model.Ensaio;
import java.util.List;

/**
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author @Andre
 */
public interface EnsaioDAO {

    /**
     * Faz a inserção ou atualização
     *
     * @param ensaio
     * @return referência atualizada do objeto.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    Ensaio save(Ensaio ensaio);

    /**
     * Exclui o registro da mercadoria na base de dados
     *
     * @param ensaio
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    void remove(Ensaio ensaio);

    /**
     * @return Lista com todas as mercadorias cadastradas no banco de dados.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Ensaio> getAll();

    /**
     * @param nome Filtro da pesquisa de mercadorias.
     * @return Lista de mercadorias com filtro em nome.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Ensaio> getEnsaiosByDescricao(String Descricao);

    /**
     * @param id filtro da pesquisa.
     * @return Experimet com filtro no id, caso nao exista
     * retorna <code>null</code>.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    Ensaio findById(Integer id);
}
