package demo.controllers;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import demo.models.Comment;
import demo.forms.CommentForm;
import demo.repos.CommentRepository;


@Controller
public class MainController extends WebMvcConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(MainController.class.getName());
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
	private final CommentRepository commentsRepo;

    @Autowired
    public MainController(CommentRepository repo) {
        this.commentsRepo = repo;
    }

	public ModelAndView generateView(String viewname, CommentForm commentForm) {
		ModelAndView view = new ModelAndView(viewname);
		view.addObject("commentForm", commentForm);
		view.addObject("comments",
                        this.commentsRepo.findAllByOrderByCreatedAsc());
		view.addObject("dateFormat", this.dateFormat);

		return view;
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView listComments() {
		return this.generateView("list", new CommentForm());
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public ModelAndView addComment(@Valid CommentForm commentForm,
			BindingResult bindingResult, HttpServletRequest request) {
		if(bindingResult.hasErrors())
			return this.generateView("list", commentForm);

		logger.info("Comment " + commentForm.toString()
				+ " has been added from ip: " + request.getRemoteAddr());

		this.commentsRepo.save(new Comment(commentForm));

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("login");
	}
}
