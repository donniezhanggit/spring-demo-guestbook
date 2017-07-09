package demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.api.CommentsApi;
import demo.dto.CommentEntry;
import demo.dto.CommentInput;


@RestController
@RequestMapping("/api/comments")
public class RestCommentController {
	private final CommentsApi commentsApi;
	
	
	public RestCommentController(final CommentsApi commentsApi) {
		this.commentsApi = commentsApi;
	}

	
	@GetMapping()
	ResponseEntity<List<CommentEntry>> getComments() {
		return ResponseEntity.ok(this.commentsApi.getComments());
	}

	
	@GetMapping(value="/{id}")
	ResponseEntity<CommentEntry> getComment(@PathVariable("id") final Long id) {
		final CommentEntry entry = this.commentsApi.getComment(id); 
		
		if(entry == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
		return ResponseEntity.ok(entry);
	}
	
	
	@PostMapping
	ResponseEntity<Long> createComment(@RequestBody final CommentInput input) {
		final CommentEntry entry = this.commentsApi.createComment(input);
		
		return ResponseEntity.ok(entry.getId());
	}
}
