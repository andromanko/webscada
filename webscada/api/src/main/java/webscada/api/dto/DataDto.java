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
public class DataDto {

	private long id;
	private int value;
	private String name;
	private String units;

}
