package dr.dao;

import dr.model.AbstractEntity;
import java.lang.reflect.ParameterizedType;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Componente <code>DAO</code> (design pattern: <code>Data Access Object</code>) base da aplicação.
 *
 * @author @Andre
 *
 * @param <Entity> determina o tipo da entidade, deve herdar <code>AbstractEntity</code>.
 * @param <PK> determina o tipo da chave primária (Primary Key) da entidade, deve herdar <code>Number</code>.
 */
public abstract class AbstractDAO<Entity extends AbstractEntity, PK extends Number> {

    private Class<Entity> entityClass;
    private EntityManager em;

    /**
     * A dependência para
     * <code>EntityManager</code> deve ser resolvida durante a construção do
     * <code>AbstractDAO</code>.
     *
     * @param em referência para o <code>EntityManager</code>.
     */
    public AbstractDAO(EntityManager em) {
        this.em = em;

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<Entity>) genericSuperclass.getActualTypeArguments()[0];
    }

    /**
     * Faz o <code>insert</code> caso a entidade ainda não tenha sido persistida (
     * <code> id == null </code>), ou <code>update</code> caso contrário.
     *
     * @param e indica a entidade que deverá ser persistida.
     * @return a referência atual da entidade.
     */
    public Entity save(Entity e) {
        if (e.getId() != null) {
            return em.merge(e);
        } else {
            em.persist(e);
            return e;
        }
    }

    /**
     * Faz o <code>delete</code> da entidade no banco de dados.
     * @param e
     */
    public void remove(Entity e) {
        em.remove(e);
    }

    /**
     * Busca o registro por <code>id</code> (primary key).
     *
     * @param id filtro da consulta.
     * @return a entidade encontrada de acordo com o <code>id</id>.
     * @throws <code>RuntimeException</code> caso o não exista registro para esse id.
     */
    public Entity findById(PK id) {
        return em.find(entityClass, id);
    }

    /**
     * @return uma coleção (<code>List</code>) com todos os registro da entidade
     * armazenados no banco de dados. Caso não existam registros, retorna uma
     * coleção vazia.
     */
    public List<Entity> getAll() {
        Query query = getPersistenceContext().createQuery("SELECT o FROM " + entityClass.getName() + " o");
        return (List<Entity>) query.getResultList();
    }

    /**
     * @return referência do componente <code>EntityManager</code>.
     */
    protected EntityManager getPersistenceContext() {
        return this.em;
    }
}
