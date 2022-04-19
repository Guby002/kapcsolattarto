package hu.futureofmedia.task.contactsapi.security.config;

import hu.futureofmedia.task.contactsapi.entities.Privilege;
import hu.futureofmedia.task.contactsapi.entities.PrivilegeName;
import hu.futureofmedia.task.contactsapi.entities.RoleName;
import hu.futureofmedia.task.contactsapi.security.AuthEntryPointJwt;
import hu.futureofmedia.task.contactsapi.security.Encoder;
import hu.futureofmedia.task.contactsapi.security.JwtTokenFilter;
import hu.futureofmedia.task.contactsapi.security.JwtTokenUtil;
import hu.futureofmedia.task.contactsapi.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
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


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //FOR SWAGGER
    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/rest-api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/rest-api-docs/swagger-config/**"
            // other public endpoints of your API may be appended to this array
    };

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
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //PUBLIC ENDPOINTS
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/api/auth/login","/api/auth/register").permitAll()
                //PRIVATE ENDPOINTS
              /*.antMatchers(HttpMethod.GET, "/api/contact/foruser/**").hasAnyAuthority(PrivilegeName.LIST.name())
                .antMatchers(HttpMethod.PUT, "/api/contact/**").hasAnyAuthority(PrivilegeName.MODIFY.name())
                .antMatchers(HttpMethod.DELETE, "/api/contact/**").hasAuthority(PrivilegeName.DELETE.name())
                .antMatchers(HttpMethod.POST, "/api/contact").hasAuthority(PrivilegeName.CREATE.name())
                .antMatchers(HttpMethod.GET, "/api/contact/user/**").hasAuthority(PrivilegeName.GET_USER_DATA.name())*/
                .anyRequest().authenticated();
        http.addFilterBefore(new JwtTokenFilter(jwtTokenUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
    }
}