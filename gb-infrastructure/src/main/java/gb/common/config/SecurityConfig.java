package gb.common.config;

import static gb.common.config.GuestBookProfiles.DEVELOPMENT;
import static gb.common.config.GuestBookProfiles.H2_INTEGRATION_TESTING;
import static gb.common.config.GuestBookProfiles.NO_DB_INTEGRATION_TESTING;
import static gb.common.config.GuestBookProfiles.PG_INTEGRATION_TESTING;
import static gb.common.config.GuestBookProfiles.PRODUCTION;
import static lombok.AccessLevel.PRIVATE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@FieldDefaults(level=PRIVATE)
@Profile(value={
        DEVELOPMENT, H2_INTEGRATION_TESTING,
        NO_DB_INTEGRATION_TESTING, PG_INTEGRATION_TESTING,
        PRODUCTION
})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;


    @Override
    public void configure(AuthenticationManagerBuilder auth)
        throws Exception {
        auth.userDetailsService(userDetailsService);

        log.info("Configuring of AuthenticationManagerBuilder finished");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .csrf().disable()
          .authorizeRequests()
            .expressionHandler(webExpressionHandler())
            .antMatchers("/api/comments").hasRole("USER")
            .antMatchers("/", "/css/**", "/images/**").permitAll()
            .anyRequest().authenticated()
          .and()
            .formLogin().loginPage("/login").permitAll()
          .and()
            .logout().permitAll();

        log.info("Configuring of HttpSecurity finished");
    }


    private
    SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        final DefaultWebSecurityExpressionHandler handler =
                        new DefaultWebSecurityExpressionHandler();

        handler.setRoleHierarchy(roleHierarchy());

        log.info("Configured DefaultWebSecurityExpressionHandler"
                + " to use roleHierarchy");

        return handler;
    }



    // Copy-pasted from {@link NoOpPasswordEncoder}. I know about deprecation.
    // But still want to get plain text password for testing purposes.
    // It is not production ready!
    @Bean
    @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
    public PasswordEncoder passwordEncoder() {
        final PasswordEncoder passwordEncoder = new PasswordEncoder() {
                @Override
                public String encode(CharSequence rawPassword) {
                        return rawPassword.toString();
                }

                @Override
                public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return rawPassword.toString().equals(encodedPassword);
                }
        };

        log.info("Configured passwordEncoder");

        return passwordEncoder;
    }


    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        final RoleHierarchyImpl rh = new RoleHierarchyImpl();

        rh.setHierarchy("ROLE_ADMIN > ROLE_USER");

        log.info("Configured RoleHierarchyImpl");

        return rh;
    }
}
