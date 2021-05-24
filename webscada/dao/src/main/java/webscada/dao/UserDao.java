package webscada.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import webscada.api.dao.IUserDao;
import webscada.entity.User;

@Transactional(readOnly = true)
@Repository
public class UserDao extends AGenericDao<User> implements IUserDao {

	public UserDao() {
		super(User.class);

	}


}
