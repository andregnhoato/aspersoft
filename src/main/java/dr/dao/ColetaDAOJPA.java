package dr.dao;

import dr.model.Coleta;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Implementa o contrato de persistência da entidade <code>Coleta</code>. 
 * Utiliza a herança para <code>AbstractDAO</code> para resolver as operações básicas de cadastro com <code>JPA</code>.
 * 
 * @see dao.ColetaDAO
 * @see dao.AbstractDAO
 * 
 * @author @andre
 */
public class ColetaDAOJPA extends AbstractDAO<Coleta, Integer> implements ColetaDAO {

    /**
     * @param em Recebe a referência para o <code>EntityManager</code>.
     */
    public ColetaDAOJPA(EntityManager em) {
        super(em);
    }
    
    /**
     * Reliza a pesquisa coletas com filtro no Ensaio (via operador
     * <code>equals</code>).
     *
     * @see dao.ColetaDAO#getColetasByEnsaio(java.lang.Integer)
     */
    @Override
    public List<Coleta> getColetasByEnsaio(Integer id) {
        if (id == null || id < 0) {
            return null;
        }
        Query query = getPersistenceContext().createQuery("SELECT c FROM Coleta c WHERE c.id_ensio = :id_ensaio");
        query.setParameter("id_ensaio", id);
        return (List<Coleta>) query.getResultList();
    }


}
