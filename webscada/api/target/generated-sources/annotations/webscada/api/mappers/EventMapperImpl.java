package webscada.api.mappers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import webscada.api.dto.EventDto;
import webscada.entity.Event;
import webscada.entity.Event.EventBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-22T23:59:45+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.11 (Ubuntu)"
)
public class EventMapperImpl implements EventMapper {

    @Override
    public Event mapEvent(EventDto source) {
        if ( source == null ) {
            return null;
        }

        EventBuilder<?, ?> event = Event.builder();

        event.id( source.getId() );
        event.dateTime( source.getDateTime() );
        event.dev( source.getDev() );
        event.user( source.getUser() );
        event.chDate( source.getChDate() );

        return event.build();
    }

    @Override
    public EventDto mapEventDto(Event source) {
        if ( source == null ) {
            return null;
        }

        EventDto eventDto = new EventDto();

        if ( source.getId() != null ) {
            eventDto.setId( source.getId() );
        }
        eventDto.setDateTime( source.getDateTime() );
        eventDto.setDev( source.getDev() );
        eventDto.setUser( source.getUser() );
        eventDto.setChDate( source.getChDate() );

        return eventDto;
    }

    @Override
    public List<Event> mapEvent(List<EventDto> sources) {
        if ( sources == null ) {
            return null;
        }

        List<Event> list = new ArrayList<Event>( sources.size() );
        for ( EventDto eventDto : sources ) {
            list.add( mapEvent( eventDto ) );
        }

        return list;
    }

    @Override
    public List<EventDto> mapEventDtos(List<Event> sources) {
        if ( sources == null ) {
            return null;
        }

        List<EventDto> list = new ArrayList<EventDto>( sources.size() );
        for ( Event event : sources ) {
            list.add( mapEventDto( event ) );
        }

        return list;
    }
}
