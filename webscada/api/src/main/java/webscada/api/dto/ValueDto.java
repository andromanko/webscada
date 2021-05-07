package webscada.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webscada.entity.Dev;
import webscada.entity.TypeEvent;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValueDto {

	private long id;
	private String name;
	private String units;
	private Dev devId;
	private String addr;
	private float max;
	private int maxMs;
	private TypeEvent maxEventId;
	private float min;
	private int minMs;
	private TypeEvent minEventId;

}
