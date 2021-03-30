package webscada.api.dao;

import java.util.List;

import webscada.entity.AEntity;

public interface IAGenericDao<T extends AEntity<Long>> {

	Class<T> getGenericClass();

	T create(T entity);

	T get(int id);

	void update(T entity);

	void delete(T entity);

	List<T> getAll();
}
