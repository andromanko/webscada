package webscada.api.utils;

import org.springframework.stereotype.Service;

import webscada.api.dto.UserDto;
import webscada.entity.User;

@Service
public interface IEmailSender {

    void sendEmailToAdmin(UserDto dto, int status) throws Exception;

    void sendEmailFromAdmin(User user, int status) throws Exception;
}
