package webscada.utils.mail;

import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import webscada.api.dto.UserDto;
import webscada.api.mappers.UserMapper;
import webscada.api.utils.IEmailSender;
import webscada.entity.User;

@Service
public class EmailSender implements IEmailSender {

	private static final String ADMIN_EMAIL_ADDRESS = "andromanko@gmail.com";

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private JavaMailSender mailSender;

	@Async
	@Override
	public void sendEmailToAdmin(UserDto dto, int status) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		String statusValue = (status == 1) ? "activate" : "deactivate";
		String text = prepareActivateRequestEmail(dto, statusValue);
		String subjext = new StringBuilder("EMAil sent automatically from WebScada by Roma for ").append(statusValue)
				.append(" the User.").toString();
		configureMimeMessageHelper(helper, dto.getEmail(), ADMIN_EMAIL_ADDRESS, text, subjext);
		mailSender.send(message);
	}

	@Async
	@Override
	public void sendEmailFromAdmin(User user, int status) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		UserDto userDto = UserMapper.mapUserDto(user);
		String text = prepareStatusChangingMailText(userDto, status);
		configureMimeMessageHelper(helper, ADMIN_EMAIL_ADDRESS, userDto.getEmail(), text,
				"This is automatic-sent letter from WebScada system");
		mailSender.send(message);
	}

	private void configureMimeMessageHelper(MimeMessageHelper helper, String mailFrom, String mailTo, String mailText,
			String mailSubject) throws MessagingException {
		helper.setFrom(mailFrom);
		helper.setTo(mailTo);
		helper.setText(mailText, true);
		helper.setSubject(mailSubject);
	}

	private String prepareStatusChangingMailText(UserDto userDto, int status) {
		String statusValue = (status == 1) ? "activated" : "deactivated";
		VelocityContext context = createVelocityContextWithBasicParameters(userDto, statusValue);
		StringWriter stringWriter = new StringWriter();
		velocityEngine.mergeTemplate("mailtemplates/statusChange.vm", "UTF-8", context, stringWriter);
		return stringWriter.toString();
	}

	private String prepareActivateRequestEmail(UserDto userDto, String statusValue) {
		VelocityContext context = createVelocityContextWithBasicParameters(userDto, statusValue);
		StringWriter stringWriter = new StringWriter();
		velocityEngine.mergeTemplate("mailtemplates/activate.vm", "UTF-8", context, stringWriter);
		return stringWriter.toString();
	}

	private VelocityContext createVelocityContextWithBasicParameters(UserDto userDto, String status) {
		VelocityContext context = new VelocityContext();
		context.put("userName", userDto.getLogin());
		context.put("status", status);
		return context;
	}
}
