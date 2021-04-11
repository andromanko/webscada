package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import webscada.entity.Dev;

public interface IDevJPADao extends JpaRepository<Dev, Integer> {

	Dev findByDevName(String dev);
}
