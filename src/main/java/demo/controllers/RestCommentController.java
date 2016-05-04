package demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.models.Comment;
import demo.repos.CommentRepository;


@RestController
@RequestMapping("/comments")
public class RestCommentController {
	@Autowired
	private CommentRepository commentRepo;
	
	@RequestMapping(method=RequestMethod.GET)
	Iterable<Comment> getComments() {
		return this.commentRepo.findAllByOrderByCreatedAsc();
	}
}
