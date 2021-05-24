package webscada.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import webscada.api.dto.UserDto;
import webscada.entity.Role;
import webscada.entity.User;

@UtilityClass
public class UserMapper {

	public User mapUser(UserDto source) {
		return User.builder().id(source.getId()).login(source.getLogin()).email(source.getEmail())
				.info(source.getInfo()).password(source.getPassword()).enabled(source.isEnabled()).build();
	}

	public UserDto mapUserDto(User source) {
		UserDto retVal = UserDto.builder().id(source.getId()).login(source.getLogin()).email(source.getEmail())
				.password(source.getPassword()).info(source.getInfo()).enabled(source.isEnabled()).build();
		String string = "";
		for (Role role : source.getRoles()) {
			string += role.toString() + "; ";
		}
		retVal.setRoles(string);

		return retVal;

	}

	public List<User> mapUsers(List<UserDto> source) {
		return source.stream().map(UserMapper::mapUser).collect(Collectors.toList());
	}

	public List<UserDto> mapUserDtos(List<User> source) {
		return source.stream().map(UserMapper::mapUserDto).collect(Collectors.toList());
	}
}
