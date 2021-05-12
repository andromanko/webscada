package webscada.api.mappers;

//попытка сделать маппер через mapstruct. Eclipse не подтянулся. Совсем.
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import webscada.api.dto.EventDto;
import webscada.entity.Event;

@Mapper
public interface EventMapper {
	
	EventMapper INSTANCE = Mappers.getMapper( EventMapper.class );
	
	Event mapEvent(EventDto source);
	
	EventDto mapEventDto(Event source);
	
	List<Event> mapEvent(List<EventDto> sources);
	
	List<EventDto> mapEventDtos(List<Event> sources);
}
