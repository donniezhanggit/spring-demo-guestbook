package gb.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public UserDetailsService userDetailsService;


    @Override
    public void configure(AuthenticationManagerBuilder auth)
        throws Exception {
        auth.userDetailsService(this.userDetailsService);

        log.debug("Configured AuthenticationManagerBuilder");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .csrf().disable()
          .authorizeRequests()
            .expressionHandler(this.webExpressionHandler())
            .antMatchers("/api/comments").hasRole("USER")
            .antMatchers("/", "/css/**", "/images/**").permitAll()
            .anyRequest().authenticated()
          .and()
            .formLogin().loginPage("/login").permitAll()
          .and()
            .logout().permitAll();

        log.debug("Configured HttpSecurity");
    }


    private
    SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler =
                        new DefaultWebSecurityExpressionHandler();

        handler.setRoleHierarchy(this.roleHierarchy());

        return handler;
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl rh = new RoleHierarchyImpl();

        rh.setHierarchy("ROLE_ADMIN > ROLE_USER");

        return rh;
    }
}
