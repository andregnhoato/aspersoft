package dr.dao;

import dr.model.Ensaio;
import dr.model.Ensaio;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
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
public class EnsaioDAOImpl implements EnsaioDAO {

    private EntityManager em;

    /**
     * @param em Recebe a referência para o <code>EntityManager</code>.
     */
    public EnsaioDAOImpl(EntityManager em) {
        super();
        this.em = em;
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
        Query query = em.createQuery("SELECT e FROM ensaio e WHERE e.descricao like :descricao");
        query.setParameter("descricao", nm.concat(descricao).concat("%"));
        return (List<Ensaio>) query.getResultList();
    }
    
    @Override
    public Ensaio save(Ensaio object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Ensaio está nulo.");
        }
        try {
            if (object.getId() != null) {
                return this.update(object);
            } else {
                this.em.getTransaction();
                if(object.getData()==null){
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    java.util.Date utilDate = cal.getTime();
                    object.setData(new Date(utilDate.getTime()));
                }
                this.em.persist(object);
                this.em.flush();
                return object;
            }
        } catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Ensaio update(Ensaio object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Ensaio está nulo.");
        }
        Ensaio c = this.em.merge(object);
        return c;
    }

    @Override
    public Ensaio findById(Serializable id) throws Exception {
        return this.em.find(Ensaio.class, id);
    }

    @Override
    public boolean remove(Ensaio object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Ensaio está nulo.");
        }
        this.em.remove(object);

        return true;
    }

    @Override
    public Collection<Ensaio> findAll() throws Exception {
        String query = "SELECT en FROM ensaio en ORDER BY en.descricao ASC";
        try {
            return this.em.createQuery(query, Ensaio.class).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<Ensaio>(0);
        }
    }


}
