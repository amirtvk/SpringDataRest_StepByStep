package ir.brochure.SpringDataRestStepByStep.entity;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "pagesJustTitleAndCommentProjection", types = {Page.class})
public interface PagesJustTitleAndCommentProjection {
    String getTitle();
    List<CommentsJustTextProjection> getComments();
}
