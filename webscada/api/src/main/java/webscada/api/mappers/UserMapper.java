package webscada.api.mappers;

import java.util.List;
import java.util.stream.Collectors;

import webscada.api.dto.UserDto;
import webscada.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public User mapUser(UserDto source) {
        return User.builder()
                .id(source.getId())
                .login(source.getLogin())
                .email(source.getEmail())
                .password(source.getPassword())
                .enabled(source.getEnabled())
                .build();
    }

    public UserDto mapUserDto(User source) {
        return UserDto.builder()
                .id(source.getId())
                .login(source.getLogin())
                .email(source.getEmail())
                .password(source.getPassword())
                .enabled(source.getEnabled())
                .build();
    }

    public List<User> mapUsers(List<UserDto> source) {
        return source.stream().map(UserMapper::mapUser).collect(Collectors.toList());
    }
    
    public List<UserDto> mapUserDtos(List<User> source) {
        return source.stream().map(UserMapper::mapUserDto).collect(Collectors.toList());
    }
}
