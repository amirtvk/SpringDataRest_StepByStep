A step by step walk-though for developing RESTfull web services using Spring Data Rest.  

Spring Data REST(SDR) is a very nice tool to develop HTTP Resources with minimum boilerplate code.
Spring Data REST is a subproject of Spring data. SDR Analyze your repositories and expose them as REST Endpoint.

1)How to expose an HTTP REST resource?
It is very easy. just create an entity using JPA @Entity annotation and an repository interface.



#### How to expose an HTTP REST resource?

```java
package ir.brochure.SpringDataRestStepByStep.entity;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Page {

    @Id
    @GeneratedValue(generator = "PageIdSeq")
    private Long id;

    @Nationalized
    private String title;

    @Nationalized
    private String description;

}

```


```java
public interface PageRepository extends CrudRepository<Page, Long> {
}
```

this is fake line

this is fake line







this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line

this is fake line



[link to section](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-expose-an-http-rest-resource)