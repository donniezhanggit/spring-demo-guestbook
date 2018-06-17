package gb.controllers;

import static lombok.AccessLevel.PRIVATE;

import java.time.format.DateTimeFormatter;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import gb.api.CommentsApi;
import gb.dto.CommentInput;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@FieldDefaults(level=PRIVATE, makeFinal=true)
@AllArgsConstructor
@Controller
public class MainController {
    DateTimeFormatter dateFormat =
        DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
    @NonNull CommentsApi commentsApi;


    public ModelAndView generateView(
            final String viewname,
            final CommentInput commentForm) {
        return new ModelAndView(viewname)
                .addObject("commentForm", commentForm)
                .addObject("comments", commentsApi.getComments())
                .addObject("dateFormat", dateFormat);
    }


    @GetMapping("/")
    public ModelAndView listComments() {
        return generateView("list", CommentInput.empty());
    }


    @PostMapping("/")
    public ModelAndView addComment(
            @ModelAttribute("commentForm")
            @Valid final CommentInput commentForm,
            final Errors bindingResult,
            final ServletRequest request) {

        if(bindingResult.hasErrors()) {
            return generateView("list", commentForm);
        }

        commentsApi.createComment(commentForm);

        log.info("Comment {} has been added from ip: {}",
                        commentForm,
                        request.getRemoteAddr());

        return new ModelAndView("redirect:/");
    }


    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
}
