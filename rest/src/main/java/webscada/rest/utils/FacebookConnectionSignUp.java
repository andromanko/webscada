package webscada.rest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;
import webscada.api.dto.UserDto;
import webscada.api.services.IUserService;
import webscada.entity.User;
@Component
public class FacebookConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private IUserService userService;

    @Override
    public String execute(Connection<?> connection) {

        Facebook facebook = (Facebook) connection.getApi();
        String[] arr = { "id", "email", "first_name"};
        User userProfile = facebook.fetchObject("me", User.class, arr);

        UserDto userDto = new UserDto();
        userDto.setLogin((userProfile.getLogin()));
        userDto.setPassword(String.valueOf(userProfile.getId()));
        userService.createUser(userDto);
        return userProfile.getLogin();
    }
}
