package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import webscada.entity.DevType;

public interface IDevTypeJPADao extends JpaRepository<DevType, Integer> {

	DevType findByDevType(String devType);
	DevType findById(int id);
}
