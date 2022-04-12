package hu.futureofmedia.task.contactsapi.security;

import hu.futureofmedia.task.contactsapi.entities.RoleName;
import hu.futureofmedia.task.contactsapi.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static java.lang.String.format;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
      //  securedEnabled = true,
     //   jsr250Enabled = true,
        prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final Encoder encoder;


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder.passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/public/login","/api/public/register").permitAll()
                .antMatchers(
                        HttpMethod.GET,
//                        "/",
                        "/v2/api-docs",           // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/swagger-ui/**",  // swagger-ui resources
                        "/rest-api-docs/**",
                        "/configuration/**",      // swagger configuration
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()

               // .antMatchers("/api/contact/**").permitAll()
                //PRIVATE ENDPOINTS
                .antMatchers(HttpMethod.GET, "/api/contact/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/**").hasAnyRole(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/contact/**").permitAll()//.hasRole(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/contact/**").hasAuthority(RoleName.USER.name())//.hasRole(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/api/contact/user/**").permitAll()//.hasRole(RoleName.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/api/contact/user/**").hasRole(RoleName.ADMIN.name())
                .anyRequest().authenticated();
        http.addFilterBefore(new JwtTokenFilter(jwtTokenUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
    }
}