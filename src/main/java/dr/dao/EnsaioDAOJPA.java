package dr.dao;

import dr.model.Experiment;
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
public class ExperimentDAOJPA extends AbstractDAO<Experiment, Integer> implements ExperimentDAO {

    /**
     * @param em Recebe a referência para o <code>EntityManager</code>.
     */
    public ExperimentDAOJPA(EntityManager em) {
        super(em);
    }


}
