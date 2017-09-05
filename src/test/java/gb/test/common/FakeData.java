package gb.test.common;

import liquibase.util.StringUtils;

public class FakeData {
    public static String stringWithLength(int length) {
        return StringUtils.repeat("A", length);
    }
}
