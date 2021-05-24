package webscada.services.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import webscada.api.dao.IDevJPADao;
import webscada.api.dao.IEventJPADao;
import webscada.api.dao.IEventTypeJPADao;
import webscada.api.dao.IUserJPADao;
import webscada.api.dto.EventDto;
import webscada.api.dto.EventStringDto;
import webscada.api.mappers.EventMapper;
import webscada.api.mappers.EventStringMapper;
import webscada.api.services.IEventService;
import webscada.entity.Event;

@Service
public class EventService implements IEventService {

	@Autowired
	private IEventJPADao eventDao;

	@Autowired
	private IUserJPADao userDao;
	
	@Autowired
	private IDevJPADao devDao;
	
	@Autowired
	private IEventTypeJPADao eventTypeDao;

	@Override
	public EventDto createEvent(int typeId) {
		return EventMapper.INSTANCE.mapEventDto(this.eventDao.save(createBasicEvent(typeId)));
	}
	@Override
	public EventDto createEvent(int typeId, long userId) {
		Event entity = createBasicEvent(typeId);
		entity.setUser(userDao.findById(userId).orElse(null));
		return EventMapper.INSTANCE.mapEventDto(this.eventDao.save(entity));
	}

	@Override
	public EventDto createEvent(int typeId, long userId, long devId) {
		Event entity = createBasicEvent(typeId);
		entity.setUser(userDao.findById(userId).orElse(null));
		entity.setDev(devDao.findById(devId));
		return EventMapper.INSTANCE.mapEventDto(this.eventDao.save(entity));
	}

	private Event createBasicEvent(int typeId) {
		return Event.builder().dateTime(new Date()).eventType(this.eventTypeDao.getOne(typeId)).build();
	}

	@Override
	public List<EventDto> getEvents() {
		return EventMapper.INSTANCE.mapEventDtos(eventDao.findAll());
	}
	

	
	@Override
	public List<EventStringDto> getEventsString(int pageNumber, int pageSize) {
		
//		long amount = eventDao.count();
//		long amountPages = amount/pageSize;
//		if (amount % pageSize !=0) { amountPages++; } 
//		Pageable pageable = new Pageable();
//		pageable.

		Page<Event> events = eventDao.findAll(PageRequest.of(pageNumber-1, pageSize,Sort.by("id").descending()));
		
		return EventStringMapper.mapEventStringDtos(events.toList());
	}
	
	@Override
	public List<EventStringDto> getEventsString() {
		
//		Page<Event> events = eventDao.findAll(PageRequest.of(3, 10));
//		
//		return EventStringMapper.mapEventStringDtos(events.toList());
		return EventStringMapper.mapEventStringDtos(eventDao.findAll());
		
	}
}
