package io.mountblue.reddit.redditClone.security;

import io.mountblue.reddit.redditClone.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(bCryptPasswordEncoder());
        return auth;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationSuccessHandler authenticationSuccessHandler,
                                            AuthenticationProvider authenticationProvider)throws Exception {
        http.authorizeHttpRequests(customizer ->
                                customizer
//                                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
//                                        .requestMatchers(HttpMethod.POST, "/**").hasRole("REDDITOR")
                                .requestMatchers("/login-page", "/logout","/register","/user/new").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form->
                        form.loginPage("/login-page")
                                .loginProcessingUrl("/authenticateTheUser").permitAll()
                                .successHandler(authenticationSuccessHandler)
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login-page")
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider);
        return http.build();
    }
}
