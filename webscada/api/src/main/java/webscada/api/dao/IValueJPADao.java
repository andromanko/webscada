package webscada.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import webscada.entity.Dev;
import webscada.entity.Value;

public interface IValueJPADao extends JpaRepository<Value, Long> {

	List<Value> findByDevId(Dev dev);
}