package webscada;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    @Autowired
    private ApplicationContext appContext;
    
	public static void main(String[] args) {
//		ClassLoader cl=ClassLoader.getSystemClassLoader();
//		URL[] urls=((URLClassLoader)cl).getURLs();
//		Arrays.stream(urls)
//			.filter(u-> !u.getFile().contains(".jar"))
//			.forEach(u->System.out.println(u.getPath()));

		SpringApplication.run(Application.class, args);
		System.out.println("webScada Started!!!");
	}
}
