package gb.common.jackson;

import java.io.IOException;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


/**
 * Global string trimming of leading and trailing white spaces for JSON
 * deserialization.
 * We can throw away validation tests for string inputs with leading and
 * trailing white spaces.
 *
 * Examples:
 *  - string ' a' becomes 'a';
 *  - 'a ' -> 'a';
 *  - '  ab c  ' -> 'ab c';
 *
 * @author whitesquall
 *
 */
public class StringTrimmer extends SimpleModule {
    private static final long serialVersionUID = -2075474664226333353L;


    @SuppressFBWarnings({"SE_INNER_CLASS", "SIC_INNER_SHOULD_BE_STATIC_ANON"})
    public StringTrimmer() {
        addDeserializer(String.class,
                new StdScalarDeserializer<String>(String.class) {
            private static final long serialVersionUID = -8510662952179861274L;


            @Override
            public String
            deserialize(final JsonParser p, final DeserializationContext ctxt)
                    throws IOException {
                return StringUtils.trimWhitespace(p.getValueAsString());
            }
        });
    }
}
