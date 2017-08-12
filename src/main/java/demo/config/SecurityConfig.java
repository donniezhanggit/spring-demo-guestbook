package demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {
        auth
          .inMemoryAuthentication()
            .withUser("user").password("password").roles("USER")
          .and()
            .withUser("admin").password("password")
            .roles("ADMIN", "ACTUATOR");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .authorizeRequests()
            .expressionHandler(this.webExpressionHandler())
            .antMatchers("/api/comments").hasRole("USER")
            .antMatchers("/", "/css/**", "/images/**").permitAll()
            .anyRequest().authenticated()
          .and()
            .formLogin().loginPage("/login").permitAll()
          .and()
            .logout().permitAll();
    }


    private
    SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler =
                        new DefaultWebSecurityExpressionHandler();

        handler.setRoleHierarchy(this.roleHierarchy());

        return handler;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl rh = new RoleHierarchyImpl();

        rh.setHierarchy("ROLE_ADMIN > ROLE_USER");

        return rh;
    }
}
