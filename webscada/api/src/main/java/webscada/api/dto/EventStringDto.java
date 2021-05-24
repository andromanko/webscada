package webscada.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventStringDto {

	private int id;
	private String dateTime;
	private String dev;
	private String user;
	private String event;
	private String chDate;

}
