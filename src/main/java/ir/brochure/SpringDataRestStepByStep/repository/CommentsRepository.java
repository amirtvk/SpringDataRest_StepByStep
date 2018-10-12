package ir.brochure.SpringDataRestStepByStep.repository;

import ir.brochure.SpringDataRestStepByStep.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CommentsRepository extends PagingAndSortingRepository<Comment, Long> {

    Page<Comment> findByTextLike(@Param("text") String text, Pageable pageable);
}
