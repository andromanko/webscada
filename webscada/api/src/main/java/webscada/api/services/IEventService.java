package webscada.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import webscada.api.dto.EventDto;
import webscada.api.dto.EventStringDto;

@Service
public interface IEventService {

	EventDto createEvent(int typeId);

	EventDto createEvent(int typeId, long userId);

	EventDto createEvent(int typeId, long userId, long devId);

	List<EventDto> getEvents();

	List<EventStringDto> getEventsString(int pageNumber, int pageSize);

	List<EventStringDto> getEventsString();

}
