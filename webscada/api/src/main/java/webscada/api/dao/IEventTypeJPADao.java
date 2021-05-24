package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import webscada.entity.TypeEvent;

public interface IEventTypeJPADao extends JpaRepository<TypeEvent, Integer> {

}
