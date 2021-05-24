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
public class ValueReal {

	private long id;
	private String name;
	private String units;
	private Number number;
	private boolean high;
	private boolean low;

}
