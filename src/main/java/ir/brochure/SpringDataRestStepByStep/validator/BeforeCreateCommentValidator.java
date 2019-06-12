package ir.brochure.SpringDataRestStepByStep.validator;

import ir.brochure.SpringDataRestStepByStep.entity.Comment;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BeforeCreateCommentValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Comment.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Comment comment = (Comment)o;
        if(comment.getText() == null || comment.getText().isEmpty())
            errors.rejectValue("text", "comment.text.empty");

    }
}
