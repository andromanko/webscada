package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import webscada.entity.Value;

public interface IValueJPADao extends JpaRepository<Value, Long> {

	//Value findByDevId(long devId);
}
