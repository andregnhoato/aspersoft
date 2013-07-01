package dr.dao;

import dr.model.Mercadoria;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Implementa o contrato de persistência da entidade <code>Mercadoria</code>. 
 * Utiliza a herança para <code>AbstractDAO</code> para resolver as operações básicas de cadastro com <code>JPA</code>.
 * 
 * @see br.com.yaw.jfx.dao.MercadoriaDAO
 * @see br.com.yaw.jfx.dao.AbstractDAO
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaDAOJPA extends AbstractDAO<Mercadoria, Integer> implements MercadoriaDAO {

    /**
     * @param em Recebe a referência para o <code>EntityManager</code>.
     */
    public MercadoriaDAOJPA(EntityManager em) {
        super(em);
    }

    /**
     * Reliza a pesquisa mercadorias com filtro no nome (via operador
     * <code>like</code>).
     *
     * @see
     * br.com.yaw.sjpac.dao.MercadoriaDAO#getMercadoriasByNome(java.lang.String)
     */
    @Override
    public List<Mercadoria> getMercadoriasByNome(String nome) {
        if (nome == null || nome.isEmpty()) {
            return null;
        }
        Query query = getPersistenceContext().createQuery("SELECT o FROM Mercadoria o WHERE o.nome like :nome");
        query.setParameter("nome", nome.concat("%"));
        return (List<Mercadoria>) query.getResultList();
    }
}
