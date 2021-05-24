package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import webscada.entity.User;

public interface IUserJPADao extends JpaRepository<User, Long> {

	User findByLogin(String login);

	User findByEmail(String email);
}
