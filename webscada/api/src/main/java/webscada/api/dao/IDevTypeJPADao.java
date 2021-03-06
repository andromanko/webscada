package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import webscada.entity.DevType;

public interface IDevTypeJPADao extends JpaRepository<DevType, Integer> {
 
	DevType findById(int id);
}
