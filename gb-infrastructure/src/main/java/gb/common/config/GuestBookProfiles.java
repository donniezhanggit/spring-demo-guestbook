package gb.common.config;

import static lombok.AccessLevel.NONE;

import lombok.NoArgsConstructor;


@NoArgsConstructor(access=NONE)
public final class GuestBookProfiles { // NOSONAR
    public static final String DEVELOPMENT = "dev";
    public static final String PRODUCTION = "prod";
    public static final String PG_INTEGRATION_TESTING = "pgit";
    public static final String H2_INTEGRATION_TESTING = "h2it";
    public static final String NO_DB_INTEGRATION_TESTING = "nodb";
    public static final String FUNCTIONAL_TESTING = "func";
}
