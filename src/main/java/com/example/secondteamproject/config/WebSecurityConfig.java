package com.example.secondteamproject.config;

import com.example.secondteamproject.exception.CustomAccessDeniedHandler;
import com.example.secondteamproject.exception.CustomAuthenticationEntryPoint;
import com.example.secondteamproject.jwt.JwtAuthFilter;
import com.example.secondteamproject.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    //redis
    private final RedisTemplate redisTemplate;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
                //Swagger Ignoring set up.
                //Writer By Park
//                .requestMatchers("/v3/api-docs/**", "/configuration/ui",
//                        "/swagger-resources/**", "/configuration/security",
//                        "/swagger-ui/**", "/webjars/**", "/swagger/**");
    }

    /**
     * It serves to render (the process of creating html) the information requested by the user
     * 사용자가 요청한 정보를 랜더링 하는 역할
     * Writer By ParkW
     */
    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests()
                .antMatchers("/users/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/general/**").permitAll()
                .antMatchers("/categories/**").hasRole("ADMIN")
                .antMatchers("/seller/**").permitAll()
                //Swagger set up
                //Writer By Park
                .anyRequest().authenticated()
                .and()
                // JWT 인증/인가를 사용하기 위한 설정 // redis
                .addFilterBefore(new JwtAuthFilter(jwtUtil,redisTemplate), UsernamePasswordAuthenticationFilter.class);
        http.formLogin().disable();
        // 401 Error 처리, Authorization 즉, 인증과정에서 실패할 시 처리
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        // 403 Error 처리, 인증과는 별개로 추가적인 권한이 충족되지 않는 경우
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        return http.build();
    }

}