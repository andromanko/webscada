package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import webscada.entity.Event;

public interface IEventJPADao extends JpaRepository<Event, Integer> {
 
}
