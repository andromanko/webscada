package webscada.rest.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;
import webscada.api.dto.UserDto;
import webscada.api.services.IUserService;
//import webscada.entity.User;

@Component
public class FacebookConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private IUserService userService;

    @Override
    public String execute(Connection<?> connection) {
//Please Forgive me for this crutch 
//Fbook не фетчился на User.Login. Поэтому пришлось сделать новый объект с именем и сеттить его в ЮерДТО
        	Facebook facebook = (Facebook) connection.getApi();
        String[] arr = { "id", "email","first_name"}; //здесь нужно first name смаппить в наш login
        FbokMapper userProfile = facebook.fetchObject("me", FbokMapper.class, arr);
                
        UserDto userDto = new UserDto();
        userDto.setId(userProfile.getId());
        userDto.setLogin((userProfile.getFirst_name()));
        userDto.setEmail((userProfile.getEmail()));
        userDto.setPassword(String.valueOf(userProfile.getId()));
        userService.createUser(userDto);
        
        return userProfile.getLogin();
    }
}

