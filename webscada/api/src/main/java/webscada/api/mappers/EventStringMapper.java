package webscada.api.mappers;

import java.text.DateFormat;
import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import webscada.api.dto.EventStringDto;
import webscada.entity.Event;

@UtilityClass
public class EventStringMapper {

	public EventStringDto mapEventStringDto(Event source) {
		EventStringDto retVal = EventStringDto.builder().id(source.getId())
				.dateTime((source.getDateTime() != null) ? DateFormat.getInstance().format(source.getDateTime()) : "")
				.dev((source.getDev() != null) ? source.getDev().getDevName().toString() : "")
				.user((source.getUser() != null) ? source.getUser().getLogin().toString() : "")
				.event((source.getEventType() != null) ? source.getEventType().toString() : "")
				.chDate((source.getChDate() != null) ? source.getChDate().toString() : "").build();

		return retVal;
	}

	public List<EventStringDto> mapEventStringDtos(List<Event> source) {
		return source.stream().map(EventStringMapper::mapEventStringDto).collect(Collectors.toList());
	}
}
