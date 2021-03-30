package webscada.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import webscada.api.dao.IAGenericDao;
import webscada.entity.AEntity;

public abstract class AGenericDao<T extends AEntity<Long>> implements IAGenericDao<T> {

    private Class<T> clazz;

    public AGenericDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    @PersistenceContext
    protected EntityManager entityManager;

    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T get(int id) {
        return entityManager.find(getGenericClass(), id);
    }
    
    public void update(T entity) {
        entityManager.merge(entity);
        entityManager.flush();
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public List<T> getAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getGenericClass());
        Root<T> root = query.from(getGenericClass());
        query.select(root);
        TypedQuery<T> result = entityManager.createQuery(query);
        return result.getResultList();
    }
    
    public Class<T> getGenericClass() {
        return this.clazz;
    }
}
