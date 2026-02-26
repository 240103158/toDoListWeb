package ideaprojects.springapplicationexample.config;


import ideaprojects.springapplicationexample.Repository.UserRepository;
import ideaprojects.springapplicationexample.entity.User;
import ideaprojects.springapplicationexample.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)  throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/registration", "/error").permitAll()
                        .requestMatchers("/account", "/account/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name())
                        .requestMatchers("/admin", "/admin/**").hasRole(UserRole.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // if you don't have permission then you throw to login
                        .usernameParameter("email") // login url get email parameter from front
                        .defaultSuccessUrl("/account") // if Identification, Authentication and Authorization is done then get acccount page
                        .permitAll() // for all users
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .permitAll()
                )
                .build();
    }


    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**"); // todo for working with css files
    }


    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                User user = userRepository.
                        findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User with email = " + email + "not found."));

                Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getRole().toAuthority());

                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),  roles);
            }
        };
    }
}
