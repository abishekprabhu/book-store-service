package com.hcltech.bookstore.config;


import com.hcltech.bookstore.filter.JwtAuthRequestFilter;
import com.hcltech.bookstore.service.AuthService.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String[] SWAGGER_WHITE_LIST        = { "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**" };
    private static final String[] H2_CONSOLE_WHITE_LIST     = { "/h2-console/**" };
    private static final String[] AUTHENTICATION_WHITE_LIST = {
            "/api/v1/auth/register/author",
            "/api/v1/auth/register/customer",
            "/api/v1/auth/login",
            "/api/v1/auth/**" };

    @Autowired
    private JwtAuthRequestFilter jwtAuthRequestFilter;

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(SWAGGER_WHITE_LIST)
                        .permitAll()
                        .requestMatchers(H2_CONSOLE_WHITE_LIST)
                        .permitAll()
                        .requestMatchers(AUTHENTICATION_WHITE_LIST)
                        .permitAll()
                        .requestMatchers("/api/v1/author/**").hasRole("AUTHOR")
                        .requestMatchers("/api/v1/customer/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/v1/books").hasAnyRole("AUTHOR","CUSTOMER")
                        .requestMatchers("/api/v1/books/**").hasRole("AUTHOR")
                        .requestMatchers("/api/v1/purchases/**").hasRole("CUSTOMER")
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider(jpaUserDetailsService))
                .addFilterBefore(jwtAuthRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(JpaUserDetailsService jpaUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(jpaUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}