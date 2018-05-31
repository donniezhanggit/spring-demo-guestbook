package gb.dto;

import gb.model.User;
import io.swagger.annotations.ApiModel;
import lombok.NonNull;
import lombok.Value;


@Value
@ApiModel(value="SimpleUser", description="Simple user model")
public class SimpleUserEntry {
    Long id;
    String userName;


    public static SimpleUserEntry from(@NonNull final User user) {
        return new SimpleUserEntry(user.getId(), user.getUsername());
    }
}
