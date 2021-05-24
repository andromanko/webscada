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
public class UserDto {

	private long id;
	private String login;
	private String password;
	private String email;
	private String info;
	private boolean enabled;
	private String roles;
}
