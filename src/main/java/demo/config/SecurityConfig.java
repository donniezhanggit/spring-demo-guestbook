package demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
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
            .withUser("admin").password("password").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
          .authorizeRequests()
          .expressionHandler(this.webExpressionHandler())
          .antMatchers("/comments").hasRole("USER")
    	  .antMatchers("/", "/css/**", "/images/**").permitAll()
    	  .anyRequest().authenticated()
    	.and()
    	  .formLogin().permitAll()
    	.and()
    	  .logout().permitAll();
    }

    private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
    	DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler =
    			new DefaultWebSecurityExpressionHandler();

    	defaultWebSecurityExpressionHandler.setRoleHierarchy(this.roleHierarchy());

    	return defaultWebSecurityExpressionHandler;
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