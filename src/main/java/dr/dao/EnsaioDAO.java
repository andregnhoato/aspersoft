package dr.dao;

import dr.model.Experiment;
import java.util.List;

/**
 * 
 * <p>Define as operações basicas de cadastro (CRUD), seguindo o design pattern <code>Data Access Object</code>.</p>
 * 
 * @author @Andre
 */
public interface ExperimentDAO {

    /**
     * Faz a inserção ou atualização
     *
     * @param experiment
     * @return referência atualizada do objeto.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    Experiment save(Experiment experiment);

    /**
     * Exclui o registro da mercadoria na base de dados
     *
     * @param experiment
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    void remove(Experiment experiment);

    /**
     * @return Lista com todas as mercadorias cadastradas no banco de dados.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    List<Experiment> getAll();

    /**
     * @param nome Filtro da pesquisa de mercadorias.
     * @return Lista de mercadorias com filtro em nome.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    //List<Experiment> getMercadoriasByNome(String nome);

    /**
     * @param id filtro da pesquisa.
     * @return Experimet com filtro no id, caso nao exista
     * retorna <code>null</code>.
     * @throws <code>RuntimeException</code> se algum problema ocorrer.
     */
    Experiment findById(Integer id);
}
