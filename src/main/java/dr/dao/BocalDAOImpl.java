package dr.dao;

import dr.model.Bocal;
import dr.model.Ensaio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Implementa o contrato de persistência da entidade
 * <code>Bocal</code>. Utiliza a herança para
 * <code>AbstractDAO</code> para resolver as operações básicas de cadastro com
 * <code>JPA</code>.
 *
 * @see dao.BocalDAO
 * @see dao.AbstractDAO
 *
 * @author
 * @andre
 */
public class BocalDAOImpl implements BocalDAO {

    private EntityManager em;

    public BocalDAOImpl(EntityManager em) {
        super();
        this.em = em;
    }

    @Override
    public Bocal save(Bocal object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Ensaio está nulo.");
        }
        try {
            if (object.getId() != null) {
                return this.update(object);
            } else {
                if (!this.em.getTransaction().isActive()) {
                    this.em.getTransaction().begin();
                }
                this.em.persist(object);
                this.em.flush();
                this.em.getTransaction().commit();
                return object;
            }
        } catch (PersistenceException e) {
            this.em.getTransaction().rollback();
            throw new PersistenceException(e);
            
        }
    }

    @Override
    public Bocal update(Bocal object) throws Exception {
         if (object == null) {
            throw new Exception("O objeto Ensaio está nulo.");
        }
        Bocal b = this.em.merge(object);
        this.em.flush();
        
        return b;
    }

    @Override
    public Bocal findById(Serializable id) throws Exception {
        return this.em.find(Bocal.class, id);
    }

    @Override
    public boolean remove(Bocal object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Bocal está nulo.");
        }
        this.em.getTransaction().begin();
        this.em.remove(object);
        this.em.flush();
        this.em.getTransaction().commit();

        return true;
    }

    @Override
    public List<Bocal> findAll() throws Exception {
        String query = "SELECT bo FROM bocal bo ORDER BY bo.descricao ASC";
        try {
//            this.em.getTransaction().begin();
            return this.em.createQuery(query, Bocal.class).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>(0);
        }
    }
    
    /**
     * Reliza a pesquisa bocais com filtro no nome (via operador
     * <code>like</code>).
     *
     * @see dao.EnsaioDAO#getEnsaiosByDescricao(java.lang.String)
     */
    @Override
    public List<Bocal> getBocalByDescricao(String descricao) {
        if (descricao == null || descricao.isEmpty()) {
            return null;
        }
        String nm = "%";
        Query query = em.createQuery("SELECT b FROM bocal b WHERE b.descricao like :descricao");
        query.setParameter("descricao", nm.concat(descricao).concat("%"));
        return (List<Bocal>) query.getResultList();
    }

}
