package gb.common.dto;

import static lombok.AccessLevel.NONE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ElementKind;
import javax.validation.Path.Node;

import gb.common.validation.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;


@Value
@AllArgsConstructor(access=NONE)
public class ValidationError {
    String field;
    String code;
    String message;


    public ValidationError(@NonNull final ConstraintViolation<?> cv) {
        field = filterPath(cv);
        code = "invalid.field.value";
        message = cv.getMessage();
    }


    public ValidationError(@NonNull final InvalidArgumentException e) {
        field = null;
        code = e.getCode();
        message = e.getMessage();
    }


    private static String
    filterPath(@NonNull final ConstraintViolation<?> cv) {
        final List<Node> nodes = new ArrayList<>();

        cv.getPropertyPath().forEach((Node node) -> {
            if(node.getKind() == ElementKind.PROPERTY) {
                nodes.add(node);
            }
        });

        return nodes.stream()
                .map(Node::getName)
                .collect(Collectors.joining("."));
    }
}
