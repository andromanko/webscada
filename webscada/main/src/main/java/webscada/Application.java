package webscada;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;
import webscada.api.services.IEventService;
import webscada.services.services.UserService;

@Slf4j
@SpringBootApplication
public class Application {

    @Autowired
    private ApplicationContext appContext;
    
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		log.info("webScada Started!!!");
	}
}
