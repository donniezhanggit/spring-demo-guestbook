package gb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


@RestController
@RequestMapping("/api/comments")
public class CommentsController extends BaseController {
    private final CommentsApi commentsApi;


    public CommentsController(final CommentsApi commentsApi) {
        this.commentsApi = commentsApi;
    }


    @GetMapping
    @ApiOperation(nickname="getComments", value="List all comments")
    public ResponseEntity<List<CommentEntry>> getComments() {
        final List<CommentEntry> comments = this.commentsApi.getComments();

        return ResponseEntity.ok(comments);
    }


    @GetMapping(value="/{id}")
    @ApiOperation(nickname="getComment", value="Get comment by ID")
    public ResponseEntity<CommentEntry>
    getComment(@PathVariable final Long id) {
        final Optional<CommentEntry> entry = this.commentsApi.getComment(id);

        return this.responseFrom(entry);
    }


    @PostMapping
    @ApiOperation(nickname="createComment", value="Create a new comment")
    public ResponseEntity<CommentEntry>
    createComment(@RequestBody final CommentInput input) {
        final CommentEntry entry = this.commentsApi.createComment(input);

        return ResponseEntity.status(HttpStatus.CREATED).body(entry);
    }
}
