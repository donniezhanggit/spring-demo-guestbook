package gb.test.common;

import com.google.common.base.Strings;


public class FakeData {
    public static String stringWithLength(int length) {
        return Strings.repeat("A", length);
    }


    public static String emailWithLength(int length) {
        final StringBuilder sb = new StringBuilder();

        sb.append(stringWithLength(length));
        sb.setCharAt(length / 2, '@');

        return sb.toString();
    }
}
