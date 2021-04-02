package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import webscada.entity.Role;

public interface IRoleJPADao extends JpaRepository<Role, Integer> {

	Role findByRoleName(String roleName);
}
