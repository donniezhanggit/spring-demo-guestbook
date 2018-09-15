package gb.auth.config;

import static lombok.AccessLevel.PRIVATE;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
public class OAuthServerConfig
extends AuthorizationServerConfigurerAdapter {
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 5*60;
    public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 30*24*60*60;


    @NonNull AuthenticationManager authenticationManager;


    @Override
    public void
    configure(final AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()");
    }


    @Override
    public void
    configure(final ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.inMemory() // NOSONAR
        .withClient("testUser")
            .secret("secret")
            .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
            .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
            .scopes("read", "write")
            .authorizedGrantTypes("password", "refresh_token");
    }


    @Override
    public void
    configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
    }
}
