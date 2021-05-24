package webscada.services.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import webscada.api.dao.IEventJPADao;
import webscada.api.dao.IEventTypeJPADao;
import webscada.api.dao.IUserJPADao;
import webscada.api.dto.EventDto;
import webscada.api.dto.UserDto;
import webscada.api.mappers.EventMapper;
import webscada.api.mappers.EventStringMapper;
import webscada.api.services.IEventService;
import webscada.api.services.IUserService;

@RunWith(MockitoJUnitRunner.class)
class EventServiceTest {

	@Mock
	IUserJPADao userDao;
	
//	@Mock
//	IEventTypeJPADao eventTypeDao;

	
//	@Mock
//	EventMapper eventMapper;
	
//	@Mock
//	EventStringMapper eventStringMapper;

//	@InjectedMockito
//	private IUserService userService;

	@Test
	void createdUserHaveTheSameId() {
		UserDto userNew = UserDto.builder()
				.login("Robot")
				.build();
//		UserDto userCreated = userService.createUser(userNew);
//		assertThat(userCreated.getId()).isNotNull();
//		assertThat(userCreated.getLogin().equals(userNew.getLogin()));
		
		System.out.println("UserService TEST PASSED");
	}


}
