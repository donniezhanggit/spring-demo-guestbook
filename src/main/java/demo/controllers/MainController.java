package demo.controllers;

import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import demo.api.CommentsApi;
import demo.dto.CommentInput;


@Controller
public class MainController extends WebMvcConfigurerAdapter {
    private final Logger logger = LoggerFactory
        .getLogger(this.getClass().getName());
    private final DateTimeFormatter dateFormat =
        DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
    private final CommentsApi commentsApi;


    public MainController(final CommentsApi commentsApi) {
        this.commentsApi = commentsApi;
    }


    public ModelAndView generateView(final String viewname,
                                     final CommentInput commentForm) {
        final ModelAndView view = new ModelAndView(viewname);

        view.addObject("commentForm", commentForm);
        view.addObject("comments", this.commentsApi.getComments());
        view.addObject("dateFormat", this.dateFormat);

        return view;
    }


    @GetMapping(value="/")
    public ModelAndView listComments() {
        return this.generateView("list", new CommentInput());
    }


    @PostMapping(value="/")
    public ModelAndView addComment(@Valid final CommentInput commentInput,
            final BindingResult bindingResult, final HttpServletRequest request) {
        if(bindingResult.hasErrors())
            return this.generateView("list", commentInput);

        this.commentsApi.createComment(commentInput);

        this.logger.info("Comment " + commentInput.toString()
                         + " has been added from ip: " + request.getRemoteAddr());

        return new ModelAndView("redirect:/");
    }


    @GetMapping(value="/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
