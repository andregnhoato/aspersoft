package dr.dao;

import dr.model.Ensaio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

/**
 * Implementa o contrato de persistência da entidade
 * <code>Ensaio</code>. Utiliza a herança para
 * <code>AbstractDAO</code> para resolver as operações básicas de cadastro com
 * <code>JPA</code>.
 *
 * @see dao.EnsaioDAO
 * @see dao.AbstractDAO
 *
 * @author
 * @andre
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
//                if (!this.em.getTransaction().isActive()) {
//                    this.em.getTransaction().begin();
//                }
                this.em.persist(object);
                this.em.flush();
//                this.em.getTransaction().commit();
                return object;
            }
        } catch (PersistenceException e) {
            if (e instanceof TransactionRequiredException) {
                return saveWhitTransation(object);
            } else {
                throw new PersistenceException(e);
            }

        }
    }

    public Ensaio saveWhitTransation(Ensaio object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Ensaio está nulo.");
        }
        try {
            if (object.getId() != null) {
                return this.update(object);
            } else {
                this.em.getTransaction().begin();
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
    public Ensaio update(Ensaio object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Ensaio está nulo.");
        }
        Ensaio e = this.em.merge(object);
        this.em.flush();

        return e;
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
        this.em.getTransaction().begin();
        Query query = this.em.createQuery("delete from coleta co where co.ensaio.id = :idEnsaio");
        query.setParameter("idEnsaio", object.getId());
        query.executeUpdate();
        this.em.remove(object);
        this.em.flush();
        this.em.getTransaction().commit();

        return true;
    }

    @Override
    public List<Ensaio> findAll() throws Exception {
        String query = "SELECT en FROM ensaio en ORDER BY en.descricao ASC";
        try {
//            em.getTransaction().begin();
            return this.em.createQuery(query, Ensaio.class).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<Ensaio>(0);
        }
    }

    @Override
    public List<Ensaio> getEnsaiosByWhere(String where) {
        if (where == null || where.isEmpty()) {
            return null;
        }
        System.out.println("SELECT e FROM ensaio e " + where);
        Query query = em.createQuery("SELECT e FROM ensaio e " + where);
//        query.setParameter("where", where );//nm.concat(descricao).concat("%"));

        return (List<Ensaio>) query.getResultList();

    }
}
