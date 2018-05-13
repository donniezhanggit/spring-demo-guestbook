package gb.common.dto;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;


@Value
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal=true)
public class CodeAndMessage {
	String code;
	String message;
}
