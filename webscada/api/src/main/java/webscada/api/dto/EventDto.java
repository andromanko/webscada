package webscada.api.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webscada.entity.Dev;
import webscada.entity.TypeEvent;
import webscada.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {

	private int id;
	private Date dateTime;
	private Dev dev;
	private User user;
	private TypeEvent event;
	private Date chDate;

}
