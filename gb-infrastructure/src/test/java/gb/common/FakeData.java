package gb.common;

import static lombok.AccessLevel.NONE;

import com.google.common.base.Strings;

import lombok.NoArgsConstructor;
import lombok.val;


@NoArgsConstructor(access=NONE)
public final class FakeData {
    public static String stringWithLength(final int length) {
        return Strings.repeat("A", length);
    }


    public static String emailWithLength(final int length) {
        val sb = new StringBuilder();

        sb.append(stringWithLength(length));
        sb.setCharAt(length / 2, '@');

        return sb.toString();
    }
}
