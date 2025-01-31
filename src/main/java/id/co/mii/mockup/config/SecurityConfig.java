package id.co.mii.mockup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .requestMatchers("/hidden-basic-auth/**").authenticated() // Protect this endpoint
            .anyRequest().permitAll()
            // .anyRequest().authenticated() // Require authentication for all requests            
            .and()
            .httpBasic(); // Enable Basic Authentication
        return http.build();
    }
}

