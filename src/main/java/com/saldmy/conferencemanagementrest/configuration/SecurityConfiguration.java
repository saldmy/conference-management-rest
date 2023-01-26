package com.saldmy.conferencemanagementrest.configuration;

import com.saldmy.conferencemanagementrest.security.AppBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AppBasicAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfiguration(AppBasicAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/conferences").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/conferences/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/conferences/{id}/status").hasRole("ADMIN")
                .antMatchers(
                        "/users",
                        "/users/{id}",
                        "/registrations",
                        "/registrations/u={userId}c={conferenceId}"
                    ).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
            .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

}
