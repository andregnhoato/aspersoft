package dr.dao;

import dr.model.Ensaio;

import javax.persistence.EntityManager;

/**
 * Implementa o contrato de persistência da entidade <code>Mercadoria</code>. 
 * Utiliza a herança para <code>AbstractDAO</code> para resolver as operações básicas de cadastro com <code>JPA</code>.
 * 
 * @see br.com.yaw.jfx.dao.MercadoriaDAO
 * @see br.com.yaw.jfx.dao.AbstractDAO
 * 
 * @author YaW Tecnologia
 */
public class EnsaioDAOJPA extends AbstractDAO<Ensaio, Integer> implements EnsaioDAO {

    /**
     * @param em Recebe a referência para o <code>EntityManager</code>.
     */
    public EnsaioDAOJPA(EntityManager em) {
        super(em);
    }


}
