package dr.dao;

import dr.model.Combinacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

/**
 * Implementa o contrato de persistência da entidade
 * <code>Combinacao</code>. Utiliza a herança para
 * <code>AbstractDAO</code> para resolver as operações básicas de cadastro com
 * <code>JPA</code>.
 *
 * @see dao.CombinacaoDAO
 * @see dao.AbstractDAO
 *
 * @author
 * @andre
 */
public class CombinacaoDAOImpl implements CombinacaoDAO {

    private EntityManager em;

    public CombinacaoDAOImpl(EntityManager em) {
        super();
        this.em = em;
    }

    @Override
    public Combinacao save(Combinacao object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Combinacao está nulo.");
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
    public Combinacao update(Combinacao object) throws Exception {
         if (object == null) {
            throw new Exception("O objeto Combinacao está nulo.");
        }
        Combinacao b = this.em.merge(object);
        this.em.flush();
        
        return b;
    }

    @Override
    public Combinacao findById(Serializable id) throws Exception {
        return this.em.find(Combinacao.class, id);
    }

    @Override
    public boolean remove(Combinacao object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Combinacao está nulo.");
        }
        this.em.getTransaction().begin();
        this.em.remove(object);
        this.em.flush();
        this.em.getTransaction().commit();

        return true;
    }

    @Override
    public List<Combinacao> findAll() throws Exception {
        String query = "SELECT co FROM combinacao co ORDER BY co.bocal.descricao, co.quebraJato.descricao ASC";
        try {
            this.em.getTransaction().begin();
            return this.em.createQuery(query, Combinacao.class).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>(0);
        }
    }

}
