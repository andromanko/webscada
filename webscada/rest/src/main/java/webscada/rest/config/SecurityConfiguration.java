package webscada.rest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

import webscada.rest.utils.FacebookConnectionSignUp;
import webscada.rest.utils.FacebookSignInAdapter;

@EnableScheduling
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private DataSource dataSource;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/", "/signup/**", "/signin/**", "/js/**", "/styles/**", "/images/**").permitAll()
				.antMatchers("/devices/**", "/values/**").hasAnyRole("ENGINEER", "DEVELOPER").antMatchers("/monitor/**")
				.hasAnyRole("VIEWER", "ENGINEER", "ADMIN", "DEVELOPER", "OPERATOR")
				.antMatchers("/admin/**", "/users/**").hasAnyRole("ADMIN", "DEVELOPER").anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll().and().logout().invalidateHttpSession(true)
				.clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").permitAll().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication().dataSource(dataSource)
				.authoritiesByUsernameQuery("SELECT user.login as username, role.role as role FROM user "
						+ "INNER JOIN user_role ON user.id = user_role.user_id "
						+ "INNER JOIN role ON user_role.role_id = role.id " + "WHERE user.login = ?")
				.usersByUsernameQuery("select login, password, 1 as enabled from user where user.login = ?");
	}

	@Bean
	public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator,
			FacebookConnectionSignUp facebookConnectionSignUp, UsersConnectionRepository usersConnectionRepository) {

		((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignUp);
		ProviderSignInController providerSignInController = new ProviderSignInController(connectionFactoryLocator,
				usersConnectionRepository, new FacebookSignInAdapter());
		providerSignInController.setPostSignInUrl("/monitor");
		return providerSignInController;
	}

	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry connectionFactoryRegistry = new ConnectionFactoryRegistry();
		connectionFactoryRegistry.addConnectionFactory(facebookConnectionFactory());
		return connectionFactoryRegistry;
	}

	@Bean
	public FacebookConnectionFactory facebookConnectionFactory() {
		FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory("2301685550141416",
				"9f916b87020b9050233bf71d96a90aaf");
		facebookConnectionFactory.setScope("email, public_profile");
		return facebookConnectionFactory;
	}

	@Bean
	public UsersConnectionRepository usersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		return new InMemoryUsersConnectionRepository(connectionFactoryLocator());
	}

}
