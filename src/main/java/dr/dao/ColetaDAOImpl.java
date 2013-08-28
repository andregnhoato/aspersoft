package dr.dao;

import dr.model.Coleta;
import dr.model.Ensaio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Implementa o contrato de persistência da entidade
 * <code>Coleta</code>. Utiliza a herança para
 * <code>AbstractDAO</code> para resolver as operações básicas de cadastro com
 * <code>JPA</code>.
 *
 * @see dao.ColetaDAO
 * @see dao.AbstractDAO
 *
 * @author
 * @andre
 */
public class ColetaDAOImpl implements ColetaDAO {

    private EntityManager em;

    public ColetaDAOImpl(EntityManager em) {
        super();
        this.em = em;
    }

    /**
     * Reliza a pesquisa coletas com filtro no Ensaio (via operador
     * <code>equals</code>).
     *
     * @see dao.ColetaDAO#getColetasByEnsaio(java.lang.Integer)
     */
    @Override
    public List<Coleta> findColetasByEnsaio(Ensaio e) throws Exception{
        if (e == null && e.getId() != null) {
            throw new Exception("O objeto Coleta está nulo.");
//            return null;
        }
        Query query = em.createQuery("SELECT co FROM coleta co WHERE co.ensaio.id = :id");
        query.setParameter("id", e.getId());
        return (List<Coleta>) query.getResultList();
    }

    @Override
    public Coleta save(Coleta object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Coleta está nulo.");
        }
        try {
            if (object.getId() != null) {
                return this.update(object);
            } else {
                this.em.getTransaction();
                this.em.persist(object);
                this.em.flush();
                return object;
            }
        } catch (PersistenceException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public Coleta update(Coleta object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Coleta está nulo.");
        }
        Coleta c = this.em.merge(object);
        return c;
    }

    @Override
    public Coleta findById(Serializable id) throws Exception {
        return this.em.find(Coleta.class, id);
    }

    @Override
    public boolean remove(Coleta object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Coleta está nulo.");
        }
        this.em.remove(object);

        return true;
    }

    @Override
    public Collection<Coleta> findAll() throws Exception {
        String query = "SELECT co FROM coleta co ORDER BY co.linha, co.coluna ASC";
        try {
            return this.em.createQuery(query, Coleta.class).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<Coleta>(0);
        }
    }
}
