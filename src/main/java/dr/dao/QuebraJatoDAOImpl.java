package dr.dao;

import dr.model.QuebraJato;
import dr.model.QuebraJato;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Implementa o contrato de persistência da entidade
 * <code>QuebraJato</code>. Utiliza a herança para
 * <code>AbstractDAO</code> para resolver as operações básicas de cadastro com
 * <code>JPA</code>.
 *
 * @see dao.QuebraJatoDAO
 * @see dao.AbstractDAO
 *
 * @author
 * @andre
 */
public class QuebraJatoDAOImpl implements QuebraJatoDAO {

    private EntityManager em;

    public QuebraJatoDAOImpl(EntityManager em) {
        super();
        this.em = em;
    }

    @Override
    public QuebraJato save(QuebraJato object) throws Exception {
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
    public QuebraJato update(QuebraJato object) throws Exception {
         if (object == null) {
            throw new Exception("O objeto Quebra Jato está nulo.");
        }
        QuebraJato b = this.em.merge(object);
        this.em.flush();
        
        return b;
    }

    @Override
    public QuebraJato findById(Serializable id) throws Exception {
        return this.em.find(QuebraJato.class, id);
    }

    @Override
    public boolean remove(QuebraJato object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto QuebraJato está nulo.");
        }
        this.em.getTransaction().begin();
        this.em.remove(object);
        this.em.flush();
        this.em.getTransaction().commit();

        return true;
    }

    @Override
    public List<QuebraJato> findAll() throws Exception {
        String query = "SELECT qj FROM quebra_jato qj ORDER BY qj.descricao ASC";
        try {
//            this.em.getTransaction().begin();
            return this.em.createQuery(query, QuebraJato.class).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>(0);
        }
    }
    
    /**
     * Reliza a pesquisa ensaios com filtro no nome (via operador
     * <code>like</code>).
     *
     * @see dao.EnsaioDAO#getEnsaiosByDescricao(java.lang.String)
     */
    @Override
    public List<QuebraJato> getQuebraJatoByDescricao(String descricao) {
        if (descricao == null || descricao.isEmpty()) {
            return null;
        }
        String nm = "%";
        Query query = em.createQuery("SELECT qj FROM quebra_jato qj WHERE qj.descricao like :descricao");
        query.setParameter("descricao", nm.concat(descricao).concat("%"));
        return (List<QuebraJato>) query.getResultList();
    }

}
