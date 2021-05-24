package webscada.api.dao;

import java.util.List;

import webscada.entity.AEntity;

public interface IAGenericDao<T extends AEntity<Long>> {

	Class<T> getGenericClass();

	T create(T entity);

	T findById(long id);

	void update(T entity);

	void deleteById(long id);

	List<T> findAll();
}
