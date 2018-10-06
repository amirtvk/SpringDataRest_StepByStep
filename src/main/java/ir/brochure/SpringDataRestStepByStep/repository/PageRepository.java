package ir.brochure.SpringDataRestStepByStep.repository;

import ir.brochure.SpringDataRestStepByStep.entity.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(path = "blogs")
public interface PageRepository extends CrudRepository<Page, Long> {

    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

    List<Page> findByTitleLike(@Param("title") String title);
}


