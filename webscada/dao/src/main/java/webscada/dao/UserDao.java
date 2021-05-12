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

//    public User getByName(String firstName) throws NoResultException {
//        try {
//            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//            CriteriaQuery<User> query = builder.createQuery(getGenericClass());
//            Root<User> companyDetailsRoot = query.from(User.class);
//            query.select(companyDetailsRoot).where(builder.equal(companyDetailsRoot.get(User_.firstName), firstName));
//            TypedQuery<User> result = entityManager.createQuery(query);
//            return result.getSingleResult();
//        } catch (NoResultException e) {
//            throw new NoResultException(e.getMessage());
//        }
//    }
}
