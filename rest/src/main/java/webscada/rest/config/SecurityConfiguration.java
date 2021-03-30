package webscada.rest.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
        .antMatchers("/", "/signup/**", "/js/**", "/styles/**", "/images/**").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated().and().formLogin().loginPage("/login")
        .permitAll().and().logout().invalidateHttpSession(true).clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll()
        .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
//                .antMatchers("/", "/signup/**", "/js/**", "/styles/**", "/images/**").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated()
//                .antMatchers("/setup/**").hasRole("ENGINEER").anyRequest().authenticated()
//                .antMatchers("/control/**").hasAnyRole("ENGINEER", "OPERATOR").anyRequest().authenticated()
//                .and().formLogin().loginPage("/login").permitAll()//перебрасывает в логин если не авторизован?
//                .and().logout().invalidateHttpSession(true).clearAuthentication(true)//что делать при логауте - рвется сессия и чистится контекст аутентикации
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll()
//                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }
//далее прописывается - как проверяется есть ли у юзера какие-либо роли: //можно сделать хардкодом "если юзер РОМА - то ему можно всё" ))
    //Этот запрос: вызвается когда юзер заходит на URL и  идет сравнение с .hasRole
//            .withDefaultSchema()
//        .withUser(User.withUsername("user")
//          .password(passwordEncoder().encode("pass"))
//          .roles("USER"));

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.jdbcAuthentication()//авторизовываемся через запрос к БД
        .dataSource(dataSource) //тот самый датасорс который поднимается в контексте при application.properties
        .authoritiesByUsernameQuery( //полномочия по запросу 
                "SELECT user.login as username, role.role as role FROM user " //взять логин из таб Юзеров 
                + "INNER JOIN user_role ON user.id = user_role.user_id "//соо
                + "INNER JOIN role ON user_role.role_id = role.id "
                + "WHERE user.login = ?") //? - это динамически подставляемый юзенрейм
        .usersByUsernameQuery("select login, password, 1 as enabled from user where user.login = ?");//тут сверка пароля//
    }//select login, password, 1 as enabled from user where user.login="q" - так это работает!
}
