package webscada.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import webscada.api.dto.EventDto;
import webscada.entity.Dev;
import webscada.entity.User;

@Service
public interface IEventService {

	EventDto findEvent(long id);

	EventDto createEvent(int typeId);

	EventDto createEvent(int typeId, User user);

	EventDto createEvent(int typeId, User user, Dev dev);

	List<EventDto> getEvents();

}
