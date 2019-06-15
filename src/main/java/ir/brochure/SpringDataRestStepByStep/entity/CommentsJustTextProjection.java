package ir.brochure.SpringDataRestStepByStep.entity;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "commentsJustTextProjection", types = {Comment.class})
public interface CommentsJustTextProjection {
    String getText();

}
