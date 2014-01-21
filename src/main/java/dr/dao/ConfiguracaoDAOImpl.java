package dr.dao;

import dr.controller.ConfiguracaoController;
import dr.model.Configuracao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

/**
 * Implementa o contrato de persistência da entidade
 * <code>Configuracao</code>. Utiliza a herança para
 * <code>AbstractDAO</code> para resolver as operações básicas de cadastro com
 * <code>JPA</code>.
 *
 * @see dao.ConfiguracaoDAO
 * @see dao.AbstractDAO
 *
 * @author
 * @andre
 */
public class ConfiguracaoDAOImpl implements ConfiguracaoDAO {

    private EntityManager em;

    /**
     * @param em Recebe a referência para o <code>EntityManager</code>.
     */
    public ConfiguracaoDAOImpl(EntityManager em) {
        super();
        this.em = em;
        this.insertUniqueConfiguration();
    }

    @Override
    public Configuracao save(Configuracao object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Configuracao está nulo.");
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
    public Configuracao update(Configuracao object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Configuracao está nulo.");
        }
        if (!this.em.getTransaction().isActive()) {
            this.em.getTransaction().begin();
        }
        Configuracao c = this.em.merge(object);
//        this.em.flush();
        this.em.getTransaction().commit();
        return c;
    }

    @Override
    public Configuracao findById(Serializable id) throws Exception {
        return this.em.find(Configuracao.class, id);
    }

    @Override
    public boolean remove(Configuracao object) throws Exception {
        if (object == null) {
            throw new Exception("O objeto Configuracao está nulo.");
        }
//        this.em.getTransaction().begin();
//        Query query = this.em.createQuery("delete from coleta co where co.ensaio.id = :idConfiguracao");
//        query.setParameter("idConfiguracao", object.getId());
//        query.executeUpdate();
//        this.em.remove(object);
//        this.em.flush();
//        this.em.getTransaction().commit();

        return true;
    }

    @Override
    public List<Configuracao> findAll() throws Exception {
        String query = "SELECT con FROM configuracao con";
        try {
//            em.getTransaction().begin();
            return this.em.createQuery(query, Configuracao.class).getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>(0);
        }
    }

    public void insertUniqueConfiguration() {

        try {
            if (this.findAll().size() <= 0) {
                Configuracao c = new Configuracao();
                c.setCasasDecimaisColeta(2);
                c.setCasasDecimaisDadosEstatisticos(2);
                c.setCasasDecimaisSobreposicao(2);
                c.setCasasDecimaisUniformidade(2);
                this.save(c);
            }

        } catch (Exception ex) {
            Logger.getLogger(ConfiguracaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
