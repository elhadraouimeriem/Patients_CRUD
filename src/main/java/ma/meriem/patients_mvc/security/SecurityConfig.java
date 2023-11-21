package ma.meriem.patients_mvc.security;

import lombok.AllArgsConstructor;
import ma.meriem.patients_mvc.security.service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
    private PasswordEncoder passwordEncoder;
    private UserDetailServiceImp userDetailServiceImp;

    //@Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){

        return new JdbcUserDetailsManager(dataSource);
    }
    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
     return new InMemoryUserDetailsManager(
             User.withUsername("meriem").password(passwordEncoder.encode("1234")).roles("USER").build(),//{noop} signifie ne pas encoder le mot de passe et simplement comparer les mots de passe tels qu'ils sont.
             User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
             User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()//passwordEncoder.encode("1234")on stocke dans la memoire que le hach du password

     );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll();
        httpSecurity.rememberMe();


        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**").permitAll();
       // httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
       // httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();//toutes les requetes necessite une authentification
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
        httpSecurity.userDetailsService(userDetailServiceImp);
        return httpSecurity.build();//pour créer un filtre
    }
}
