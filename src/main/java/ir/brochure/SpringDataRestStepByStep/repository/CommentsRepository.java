package ir.brochure.SpringDataRestStepByStep.repository;

import ir.brochure.SpringDataRestStepByStep.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CommentsRepository extends CrudRepository<Comment, Long> {
}
