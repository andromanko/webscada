package webscada.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import webscada.entity.Role;

public interface IRoleJPADao extends JpaRepository<Role, Long> {

	Role findByRoleName(String roleName);
}
