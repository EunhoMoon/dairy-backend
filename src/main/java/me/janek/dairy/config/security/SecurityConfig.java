package me.janek.dairy.config.security;

import lombok.RequiredArgsConstructor;
import me.janek.dairy.common.jwt.JwtAccessDeniedHandler;
import me.janek.dairy.common.jwt.JwtAuthenticationEntryPoint;
import me.janek.dairy.common.jwt.JwtTokenProvider;
import me.janek.dairy.config.jwt.JwtSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
            .ignoringRequestMatchers(
                antMatcher("/h2-console/**")
            )
            .disable()
        );

        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.authorizeHttpRequests(authorize ->
            authorize
                .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                .requestMatchers("/favicon.ico", "/error").permitAll()
                .requestMatchers("/api/sign-in", "/api/sign-up").permitAll()
                .anyRequest().authenticated()
        );

        http.exceptionHandling(configure -> configure
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
        );

        http.sessionManagement(configure -> configure.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }

}
