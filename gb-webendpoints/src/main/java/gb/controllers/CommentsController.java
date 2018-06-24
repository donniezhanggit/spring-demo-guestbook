package gb.controllers;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gb.api.CommentsApi;
import gb.common.endpoint.ResponseUtils;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@FieldDefaults(level=PRIVATE, makeFinal=true)
@RestController
@RequestMapping("/api/comments")
public class CommentsController {
    @NonNull CommentsApi commentsApi;


    @GetMapping
    @ResponseStatus(OK)
    @ApiOperation(nickname="getComments", value="List all comments")
    public List<CommentEntry> getComments() {
        return commentsApi.getComments();
    }


    @GetMapping("/{id}")
    @ApiOperation(nickname="getComment", value="Get comment by ID")
    public ResponseEntity<CommentEntry>
    getComment(@PathVariable final long id) {
        final Optional<CommentEntry> entry = commentsApi.getComment(id);

        return ResponseUtils.wrapOrNotFound(entry);
    }


    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(nickname="createComment", value="Create a new comment")
    public long createComment(@RequestBody final CommentInput input) {
        return commentsApi.createComment(input);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(nickname="removeComment",
        value="Remove an existing comment by ID")
    public void removeComment(@PathVariable final long id) {
        commentsApi.removeComment(id);
    }
}
