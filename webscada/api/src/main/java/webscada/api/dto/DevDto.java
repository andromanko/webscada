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
public class DevDto {

	private long id;
	private String devName;
	private String ip;
	private byte addr;
	private short port;
	private DevTypeDto devType;

	@Override
	public String toString() {
		return this.devName;
	}
}
