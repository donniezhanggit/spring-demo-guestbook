package gb.common.config;

import lombok.experimental.UtilityClass;


@UtilityClass
public class GuestBookProfiles {
    public final String DEVELOPMENT = "dev";
    public final String PRODUCTION = "prod";
    public final String PG_INTEGRATION_TESTING = "pgit";
    public final String H2_INTEGRATION_TESTING = "h2it";
    public final String NO_DB_INTEGRATION_TESTING = "nodb";
}
