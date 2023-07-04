package com.example.tamozhpenies.config;


import com.example.tamozhpenies.security.UserDetailsServiceImpl;
import com.example.tamozhpenies.user.Role;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

//Конфигурация Spring Security
@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http    //Пути для авторизации
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/client/**").hasRole(Role.CLIENT.name())
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/default")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));
        return http.build();
    }


    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    //Хеширование паролей
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //Встроенный аккаунт администратора
    @Bean
    UserDetailsService users() {
        UserDetails user = User.builder()
                .username("Halz")
                .password(passwordEncoder().encode("dana"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
