package gb.common;

import com.google.common.base.Strings;

import lombok.val;
import lombok.experimental.UtilityClass;


@UtilityClass
public class FakeData {
    public String stringWithLength(final int length) {
        return Strings.repeat("A", length);
    }


    public String emailWithLength(final int length) {
        val sb = new StringBuilder();

        sb.append(stringWithLength(length));
        sb.setCharAt(length / 2, '@');

        return sb.toString();
    }
}
