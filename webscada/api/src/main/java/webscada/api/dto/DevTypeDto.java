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
public class DevTypeDto {

	private int id;
	private String type;
	private String description;
	private String url;

	@Override
	public String toString() {
		return this.type;
	}
}
