package demo.controllers;

import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import demo.models.Comment;
import demo.forms.CommentForm;
import demo.repos.CommentReposotory;


@Controller
public class MainController extends WebMvcConfigurerAdapter {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

        @Autowired
	private CommentReposotory commentsRepo;

	public ModelAndView generateView(String viewname, CommentForm commentForm) {
		ModelAndView view = new ModelAndView(viewname);
		view.addObject("commentForm", commentForm);
		view.addObject("comments", this.commentsRepo.findAll());
		view.addObject("dateFormat", this.dateFormat);

		return view;
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView listComments() {
		return this.generateView("list", new CommentForm());
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public ModelAndView addComment(@Valid CommentForm commentForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors())
			return this.generateView("list", commentForm);

		this.commentsRepo.save(new Comment(commentForm));

		return new ModelAndView("redirect:/");
	}
}
