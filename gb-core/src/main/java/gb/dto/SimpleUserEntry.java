package gb.dto;

import static lombok.AccessLevel.PRIVATE;

import gb.model.User;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@Getter
@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
@ApiModel(value="SimpleUser", description="Simple user model")
public class SimpleUserEntry {
    Long id;
    String userName;


    public static SimpleUserEntry from(@NonNull User user) {
        return new SimpleUserEntry(user.getId(), user.getUsername());
    }
}
