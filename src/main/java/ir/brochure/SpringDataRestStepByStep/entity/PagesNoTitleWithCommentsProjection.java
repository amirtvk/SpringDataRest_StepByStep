package ir.brochure.SpringDataRestStepByStep.entity;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "pagesNoTitleWithCommentsProjection", types = {Page.class})
public interface PagesNoTitleWithCommentsProjection {
    String getDescription();
    List<KeyWord> getKeyWords();
    List<Comment> getComments();
}
