package gb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOG = LoggerFactory
            .getLogger(SecurityConfig.class);

    @Autowired
    public UserDetailsService userDetailsService;


    @Override
    public void configure(AuthenticationManagerBuilder auth)
        throws Exception {
        auth.userDetailsService(this.userDetailsService);

        LOG.info("Configuring of AuthenticationManagerBuilder finished");
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

        LOG.info("Configuring of HttpSecurity finished");
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
