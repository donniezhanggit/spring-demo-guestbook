package gb.controllers;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gb.api.CommentsApi;
import gb.common.endpoint.BaseController;
import gb.dto.CommentEntry;
import gb.dto.CommentInput;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level=PRIVATE, makeFinal=true)
@RestController
@RequestMapping("/api/comments")
public class CommentsController extends BaseController {
    CommentsApi commentsApi;


    public CommentsController(@Nonnull final CommentsApi commentsApi) {
        this.commentsApi = commentsApi;
    }


    @GetMapping
    @ApiOperation(nickname="getComments", value="List all comments")
    public ResponseEntity<List<CommentEntry>> getComments() {
        final List<CommentEntry> comments = commentsApi.getComments();

        return ResponseEntity.ok(comments);
    }


    @GetMapping(value="/{id}")
    @ApiOperation(nickname="getComment", value="Get comment by ID")
    public ResponseEntity<CommentEntry>
    getComment(@PathVariable final Long id) {
        final Optional<CommentEntry> entry = commentsApi.getComment(id);

        return responseFrom(entry);
    }


    @PostMapping
    @ApiOperation(nickname="createComment", value="Create a new comment")
    public ResponseEntity<Long>
    createComment(@RequestBody final CommentInput input) {
        final long id = commentsApi.createComment(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }


    @DeleteMapping(value="/{id}")
    @ApiOperation(nickname="removeComment",
        value="Remove an existing comment by ID")
    public ResponseEntity<Void>
    removeComment(@PathVariable final Long id) {
        commentsApi.removeComment(id);

        return ResponseEntity.noContent().build();
    }
}
