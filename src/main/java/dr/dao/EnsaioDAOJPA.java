package dr.dao;

import dr.model.Ensaio;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Implementa o contrato de persistência da entidade <code>Ensaio</code>. 
 * Utiliza a herança para <code>AbstractDAO</code> para resolver as operações básicas de cadastro com <code>JPA</code>.
 * 
 * @see dao.EnsaioDAO
 * @see dao.AbstractDAO
 * 
 * @author @andre
 */
public class EnsaioDAOJPA extends AbstractDAO<Ensaio, Integer> implements EnsaioDAO {

    /**
     * @param em Recebe a referência para o <code>EntityManager</code>.
     */
    public EnsaioDAOJPA(EntityManager em) {
        super(em);
    }
    
    /**
     * Reliza a pesquisa ensaios com filtro no nome (via operador
     * <code>like</code>).
     *
     * @see dao.EnsaioDAO#getEnsaiosByDescricao(java.lang.String)
     */
    @Override
    public List<Ensaio> getEnsaiosByDescricao(String descricao) {
        if (descricao == null || descricao.isEmpty()) {
            return null;
        }
        String nm = "%";
        Query query = getPersistenceContext().createQuery("SELECT e FROM Ensaio e WHERE e.descricao like :descricao");
        query.setParameter("descricao", nm.concat(descricao).concat("%"));
        return (List<Ensaio>) query.getResultList();
    }


}
