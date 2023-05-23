package net.myproject.todo.Config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity //Method base autentication
@SecurityScheme(
        //authentication for swagger
        name = "Basic",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "Basic",
        scheme = "basic"
)
public class SpringSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> {
                    //------------------------Role Based Authorization-----------------------
//                    authorize.requestMatchers(HttpMethod.POST,"/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT,"/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("ADMIN","USER");
//                    authorize.requestMatchers(HttpMethod.PATCH,"/api/**").hasAnyRole("ADMIN","USER");
//                    authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll();


                    authorize.requestMatchers("/swagger-ui/**").permitAll();
                    authorize.requestMatchers("/v3/**").permitAll();
                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return http.build();

    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // This code is for in memory authentication
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails shubham = User.builder()
//                .username("shubham")
//                .password(passwordEncoder().encode("shubham"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(shubham, admin);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
