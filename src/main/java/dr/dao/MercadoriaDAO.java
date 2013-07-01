package dr.dao;

import dr.model.Mercadoria;
import java.util.List;

/**
 * Contrato de persistência para a entidade <code>Mercadoria</code>. 
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author YaW Tecnologia
 */
public interface MercadoriaDAO {

    /**
     * Faz a inserção ou atualização da mercadoria na base de dados.
     *
     * @param mercadoria
     * @return referência atualizada do objeto.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    Mercadoria save(Mercadoria mercadoria);

    /**
     * Exclui o registro da mercadoria na base de dados
     *
     * @param mercadoria
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    void remove(Mercadoria mercadoria);

    /**
     * @return Lista com todas as mercadorias cadastradas no banco de dados.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Mercadoria> getAll();

    /**
     * @param nome Filtro da pesquisa de mercadorias.
     * @return Lista de mercadorias com filtro em nome.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Mercadoria> getMercadoriasByNome(String nome);

    /**
     * @param id filtro da pesquisa.
     * @return Mercadoria com filtro no id, caso nao exista
     * retorna <code>null</code>.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    Mercadoria findById(Integer id);
}
