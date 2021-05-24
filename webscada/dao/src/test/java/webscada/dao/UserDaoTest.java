package webscada.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import webscada.api.dao.IUserDao;
import webscada.api.dao.IUserJPADao;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDaoTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private IUserJPADao userDao;
	
	@Test
	public void injectedComponentAreNotNull() {
		assertThat(userDao).isNotNull();
		System.out.println("Dao TEST PASSED");
	}
}
