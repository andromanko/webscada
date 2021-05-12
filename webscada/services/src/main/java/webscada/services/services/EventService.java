package webscada.services.services;

import java.util.Date;
import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import webscada.api.dao.IEventJPADao;
import webscada.api.dao.IEventTypeJPADao;
import webscada.api.dto.EventDto;
import webscada.api.mappers.EventMapper;
import webscada.api.services.IEventService;
import webscada.entity.Dev;
import webscada.entity.Event;
import webscada.entity.User;

@Service
public class EventService implements IEventService {

	@Autowired
	private IEventJPADao eventDao;	

	@Autowired
	private IEventTypeJPADao eventTypeDao;
	
	@Override
	public EventDto createEvent(int typeId) {
		
		Event entity = Event.builder()
				.dateTime( new Date())
				.event_id(this.eventTypeDao.getOne(typeId))
				.build();
		Event savedEvent = this.eventDao.save(entity);
		EventDto eventDto = EventMapper.INSTANCE.mapEventDto(savedEvent);
		return eventDto;
	}
	
	@Override
	public EventDto createEvent(int typeId, User user) {
		EventDto entity =this.createEvent(typeId);
		entity.setUser_id(user);
		this.eventDao.save(EventMapper.INSTANCE.mapEvent(entity));
		return entity;
	}	

	@Override
	public EventDto createEvent(int typeId, User user, Dev dev) {
		
		EventDto entity =this.createEvent(typeId, user);
		entity.setDev_id(dev);
		this.eventDao.save(EventMapper.INSTANCE.mapEvent(entity));
		return entity;
	}	
	
	
	@Override
	public List<EventDto> getEvents() {
		return EventMapper.INSTANCE.mapEventDtos(eventDao.findAll());
	}

	@Override
	public EventDto findEvent(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
