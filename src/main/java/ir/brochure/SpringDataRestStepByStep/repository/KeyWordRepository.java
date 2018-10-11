package ir.brochure.SpringDataRestStepByStep.repository;

import ir.brochure.SpringDataRestStepByStep.entity.KeyWord;
import ir.brochure.SpringDataRestStepByStep.entity.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface KeyWordRepository extends CrudRepository<KeyWord, Long> {
}


