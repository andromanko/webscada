package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import webscada.entity.Dev;

public interface IDevJPADao extends JpaRepository<Dev, Long> {

	Dev findByDevName(String dev);

	Dev findById(long id);
}
