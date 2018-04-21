package gb.controllers;

import java.time.format.DateTimeFormatter;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import gb.api.CommentsApi;
import gb.dto.CommentInput;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class MainController extends WebMvcConfigurerAdapter {
    private final DateTimeFormatter dateFormat =
        DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
    private final CommentsApi commentsApi;


    public MainController(@Nonnull final CommentsApi commentsApi) {
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

        log.info("Comment {} has been added from ip: {}",
                        commentInput,
                        request.getRemoteAddr());

        return new ModelAndView("redirect:/");
    }


    @GetMapping(value="/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
