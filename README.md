A step by step walk-though for developing RESTfull web services using Spring Data Rest.  

Spring Data REST(SDR) is a very nice tool to develop HTTP Resources with minimum boilerplate code.
Spring Data REST is a subproject of Spring data. SDR Analyze your repositories and expose them as REST Endpoint.


[How to expose an HTTP REST resource?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-expose-an-http-rest-resource)

#### How to expose an HTTP REST resource?

It is very easy. Just create an entity using JPA @Entity annotation and an repository interface.


Page Entity:
```java
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

Page Repository:
```java
public interface PageRepository extends CrudRepository<Page, Long> {
}
```
Note that detection of repositories in code is done based on 'RepositoryDiscoveryStrategies'
we can access to HTTP resource:
```json
$curl -X GET http://127.0.0.1:7000/pages

{
  "_embedded" : {
    "pages" : [ ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:7000/pages"
    },
    "profile" : {
      "href" : "http://127.0.0.1:7000/profile/pages"
    }
  }
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



