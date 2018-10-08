package gb.dto;

import java.io.Serializable;

import gb.domain.User;
import io.swagger.annotations.ApiModel;
import lombok.NonNull;
import lombok.Value;


@Value
@ApiModel(value="SimpleUser", description="Simple user model")
public class SimpleUserEntry implements Serializable {
    private static final long serialVersionUID = -8379702900490907281L;

    Long id;
    String userName;


    public static SimpleUserEntry from(@NonNull final User user) {
        return new SimpleUserEntry(user.getId(), user.getUserName());
    }
}
