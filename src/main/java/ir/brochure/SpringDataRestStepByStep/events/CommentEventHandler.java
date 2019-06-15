package ir.brochure.SpringDataRestStepByStep.events;

import ir.brochure.SpringDataRestStepByStep.entity.Comment;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Comment.class)
public class CommentEventHandler {


    @HandleBeforeCreate
    private void handleBeforeCreate(Comment retail){
        retail.setSubmitBy(getAuthenticatedUser());
    }

    private String getAuthenticatedUser(){
        return "dummyUser";
    }


}
