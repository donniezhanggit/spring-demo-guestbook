package demo.controllers;

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

import demo.api.CommentsApi;
import demo.common.controllers.BaseController;
import demo.dto.CommentEntry;
import demo.dto.CommentInput;
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
        return ResponseEntity.ok(this.commentsApi.getComments());
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
