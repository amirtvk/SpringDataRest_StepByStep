# Spring Data REST Step By Step Guide


A step by step walk-though guide for developing RESTfull web services using Spring Data Rest. For more details go to [Spring Data REST official doc](https://docs.spring.io/spring-data/rest/docs/current/reference/html/)

Spring Data REST is a very nice tool to develop HTTP Resources with minimum boilerplate code.
Spring Data REST is a subproject of Spring data. Spring Data Rest Analyze your repositories and expose them as a REST Endpoints. 


[How to expose an HTTP REST resource?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-expose-an-http-rest-resource)

[How to customize repository access path?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-customize-repository-access-path)

[How to use POST method on a repository?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-use-post-method-on-a-repository)

[How to use GET and HEAD method on a repository?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-use-get-and-head-method-on-a-repository)

[How to use PATCH and PUT method on a repository?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-use-patch-and-put-method-on-a-repository)

[How to use DELETE method on a repository?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-use-delete-method-on-a-repository)

[How to disable a method exposure on a repository?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-disable-a-method-exposure-on-a-repository)

[How clients can discover resources based on HATEOS?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-clients-can-discover-resources-based-on-hateos)

[How to enable HAL-Browser?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-enable-hal-browser)

[How clients can find out the meta-data of resources?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-clients-can-find-out-the-meta-data-of-resources)

[How to add custom search using spring data (query creation by method name)?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-add-custom-search-using-spring-data-query-creation-by-method-name)

[How to add custom search using custom query?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-add-custom-search-using-custom-query)

[How to implements oneToMany relationship?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-implements-onetomany-relationship)

[How to update oneToMany relationship?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-update-onetomany-relationship)

[How to add a resource with embedded in-line childes (Composition)?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-add-a-resource-with-embedded-in-line-childes-composition)

[How to enable paging and sorting capability on findAll method?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-enable-paging-and-sorting-capability-on-findAll-method)

[How to add paging and sorting to custom search methods?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-enable-paging-and-sorting-capability-on-findAll-method)

[How to add validation to repositories?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-add-validation-to-repositories)

[How to use events on a repository?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-use-events-on-a-repository)

[How to define a projection for a REST resource?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-define-a-projection-for-a-rest-resource)

[How to define a nested projection for a REST resource?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-define-a-nested-projection-for-a-rest-resource)

[How to define an excerpt for a REST resource?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-define-an-excerpt-for-a-rest-resource)

[How to add enumerations fields to a REST resource?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-add-enumerations-fields-to-a-rest-resource)

[How to use ETag header in Spring Data REST?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-use-ETag-header-in-spring-data-rest)

--- --- --- ---

[How to configure Spring Data Rest as a resource server in OAuth2]

[How to use SPEL in projections?]

[How to enable H2 console?]

[How to use Spring Data Rest behind a proxy?]

[How to set DateTime format in search methods?]

[how to expose resource IDs?]

[how to disable CORS check?]


**********



### How to expose an HTTP REST resource?


It is very easy. Just create an entity using JPA `@Entity` annotation and an repository interface.


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

Now, we can access to HTTP resource:
```javascript
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

Note that detection of repositories in code is done based on `RepositoryDiscoveryStrategies`



**********

### How to customize repository access path?
Suppose you want to use another name for repository instead of default name (entity name + 's'/'es'/'ies' ...). In order to change the repository path you should use `@RepositoryRestResource` annotation.
So our page repository is going to be annotated with the `@RepositoryRestResource` :

```java
@RepositoryRestResource(path = "blogs")
public interface PageRepository extends CrudRepository<Page, Long> {
}
```

```javascript
curl -X GET http://127.0.0.1:7000/blogs
{
  "_embedded" : {
    "pages" : [ ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:7000/blogs"
    },
    "profile" : {
      "href" : "http://127.0.0.1:7000/profile/blogs"
    }
  }
}
```



**********

### How to use POST method on a repository?

A POST method is used for create a new resource in repository. for example if you want to create a new blog (page entity) just send a POST request including resource attributes in request body

```
curl -X POST http://127.0.0.1:7000/blogs -H 'Content-Type: application/json' -d '{	"title" : "Amir Super Market",	"description" : "the best market ever"}'
```
After running this command we got a `201` response code which means the resource was `created`




**********

### How to use GET and HEAD method on a repository?

Get method get one or list of resources. if you want to get list of all resources in a repository simply sent a `GET` request to the repository.

```javascript
 curl -X GET http://127.0.0.1:7000/blogs 
 {
    "_embedded": {
        "pages": [
            {
                "title": "Amir Super Market",
                "description": "the best market ever",
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/blogs/1"
                    },
                    "page": {
                        "href": "http://127.0.0.1:7000/blogs/1"
                    }
                }
            },
            {
                "title": "Amir Fast Food",
                "description": "the awsome fast food",
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/blogs/2"
                    },
                    "page": {
                        "href": "http://127.0.0.1:7000/blogs/2"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/blogs"
        },
        "profile": {
            "href": "http://127.0.0.1:7000/profile/blogs"
        }
    }
}
```
As you can see there are two resources in `pages` tag in response of `GET` request.

If you like to get details about just one resource you have to send a `GET` request on the resource URI (`_links.self.href`)

```javascript
curl -X GET http://127.0.0.1:7000/blogs/1
{
    "title": "Amir Super Market",
    "description": "the best market ever",
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/blogs/1"
        },
        "page": {
            "href": "http://127.0.0.1:7000/blogs/1"
        }
    }
}
```

`HEAD` method is used to check the availability of a resource. 

```javascript
curl -X HEAD http://127.0.0.1:7000/blogs/1
```
when you run this command, if a resource which has `identifier = 1` exists you will got a `204` response code `No Content` else the response code will be `404` `Not Found`.




**********

### How to use PATCH and PUT method on a repository?

`PATCH` method is used to partially update a resource. If you want to update a resource send a `PATCH` request to resource URI. The body of `PATCH` request have to include all attribute:newValues.

```
curl -X PATCH http://127.0.0.1:7000/blogs/1  -H 'Content-Type: application/json' -d '{ "description" : "the most bad super market ever"}'
```
after running this command description attribute of blog with identifier=1 will be updated.


`PUT` method is used to completely replace or insert new (if not exists) a resource. If you want to completely replace a resource send a `PUT` request to resource URI. The body of 'PUT' method should includes all attributes of resource.
 otherwise all missing attributes considered as `null`.

```javascript
curl -X PUT http://127.0.0.1:7000/blogs/2 -H 'Content-Type: application/json' -d '{ "title" : "Amir Pet Shop" }'
{
    "title": "Amir Pet Shop",
    "description": null,
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/blogs/2"
        },
        "page": {
            "href": "http://127.0.0.1:7000/blogs/2"
        }
    }
}
```

As you can see, a resource (identifire = 2) was replaced with new values. Because the `description` tag is omitted in request body, server considered the description as null.




**********

### How to use DELETE method on a repository?

`DELETE` method is use for delete a resource. In order to delete a resource send a `DELETE` request to resource URI.

```
curl -X DELETE http://127.0.0.1:7000/blogs/4
```
You will got a `204` `No Content` response code if server can find and delete the resource.




**********

### How to disable a method exposure on a repository?

Sometimes you don't want to expose all methods of a repository to out world. By using `@RestResource(exported = false)` annotation on repository methods you are able to prevent the method to be exported on HTTP.

For example if you don't like to expose 'DELETE' method on HTTP, you have to find the related method (or maybe methods, have a look at official documentation for more details) in repository and annotate the method/methods by
the @RestResource(exported = false) annotation.

```java
@RepositoryRestResource(path = "blogs")
public interface PageRepository extends CrudRepository<Page, Long> {
    
    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

}
```

After this change if you send a `DELETE` request on the resource

```
curl -X DELETE http://127.0.0.1:7000/blogs/1 
```

you will got a `405` response code which means `MethodNotAllowed`.


**********

### How clients can discover resources based on HATEOS?

according to `HATEOS`, all resources should be discoverable by client. Spring Data REST supports `HATEOS` through `HAL`. In Returned document there is a `_links` tag that includes self and related links to discover other resources.


**********

### How to enable HAL-Browser?

HAL-Browser is a nice web application for exploring REST APIs. By adding its dependency to the project class path, HAL-Browser will automatically added to root URL of server. Open a browser and go to

 `http://127.0.0.1:7000` 

![alt text](https://github.com/amirtvk/SpringDataRest_StepByStep/raw/master/img/halbrowser.png "HAL Browser")



**********

### How clients can find out the meta-data of resources?

Spring Data REST supports two way to get meta-data of resources. There is a profile resource for each repository that automatically generates by Spring Data REST. if you follow the profile resource you got a document in ALPS format. 

```javascript
curl -X GET http://127.0.0.1:7000/profile/blogs
{
    "alps": {
        "version": "1.0",
        "descriptor": [
            {
                "id": "page-representation",
                "href": "http://127.0.0.1:7000/profile/blogs",
                "descriptor": [
                    {
                        "name": "title",
                        "type": "SEMANTIC"
                    },
					....
					....
					....
```
The second way to provide meta data to client is JSON Schema. Just send the same request to profile resource but with additional accept header, and you got the meta data in JSON Schema format.

```javascript
curl -X GET http://127.0.0.1:7000/profile/blogs -H 'Accept: application/schema+json' 
{
    "title": "Page",
    "properties": {
        "description": {
            "title": "Description",
            "readOnly": false,
            "type": "string"
        },
        "title": {
            "title": "Title",
            "readOnly": false,
            "type": "string"
        }
    },
    "definitions": {},
    "type": "object",
    "$schema": "http://json-schema.org/draft-04/schema#"
}
```

**********

### How to add custom search using spring data (query creation by method name)?

One of the interesting features of Spring Data is creating query methods by method name. Just create a method in repository interface with respect to some naming conventions, Spring Data implements other things for you.
You can use this feature and expose the search resource method within the repository

```java
@RepositoryRestResource(path = "blogs")
public interface PageRepository extends CrudRepository<Page, Long> {

	List<Page> findByTitleLike(@Param("title") String title);
}
```

after adding this method you can use this search on blog (Page in DB) resources.

```javascript
curl -X GET 'http://127.0.0.1:7000/blogs/search/findByTitleLike?title=%25Fast%25' 
{
    "_embedded": {
        "pages": [
            {
                "title": "Amir Fast Food",
                "description": "the awsome fast food",
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/blogs/1"
                    },
                    "page": {
                        "href": "http://127.0.0.1:7000/blogs/1"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/blogs/search/findByTitleLike?title=%25Fast%25"
        }
    }
}
```

As you can see in the body of response, all blogs (pages) which the title like '%Fast%' has been returned as result.


**********


### How to add custom search using custom query?

Spring Data allows you to create repository methods using JPQL queries. These methods are also exposed on HTTP When using Spring Data REST.

Here we are going to create the same search (find pages by title) method using JPQL query. 

```java
@Query("select p from Page p where p.title like :title")
List<Page> customSearchFindByTitle(@Param("title") String title);
```

### How to implements oneToMany relationship?

In our example, suppose each page includes some comments. So we have an oneToMany relationship between `Page` and `Comment`. Lets add comment feature to our RESTfull API.

First, we add comment entity:

```java
@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(generator = "CommentIdSeq")
    private Long id;

    @Nationalized
    private String text;

}
```

and comment repository:

```java
@RepositoryRestResource
public interface CommentsRepository extends CrudRepository<Comment, Long> {
}

then we have to modify our `Page` entity to support relationship with `Comment`

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

    @OneToMany
    List<Comment> comments;
}
```

Now we can add comments:

```javascript
curl -X POST http://127.0.0.1:7000/comments -H 'Content-Type: application/json'  -d '{ "text" : "this blog is very good" }'

{
    "text": "this blog is very good",
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/comments/1"
        },
        "comment": {
            "href": "http://127.0.0.1:7000/comments/1"
        }
    }
}

curl -X POST http://127.0.0.1:7000/comments -H 'Content-Type: application/json'  -d '{ "text" : "I like the blog" }'

{
    "text": "I like the blog",
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/comments/2"
        },
        "comment": {
            "href": "http://127.0.0.1:7000/comments/2"
        }
    }
}


```

Then we can refer comments related to a blog using comment URI.

```javascript
curl -X POST http://127.0.0.1:7000/blogs -H 'Content-Type: application/json' \
-d '{ "title" : "Amir Fast Food", "description" : "the awsome fast food", "comments" : ["http://127.0.0.1:7000/comments/1", "http://127.0.0.1:7000/comments/2"] }'
```

as you can see we refer to two comment in order to add to comments collection for the blog.


**********

### How to update oneToMany relationship?

In real world, comments will submitted after the blog was created. So in this scenario we have to add a comment to existing page(blog). According to HTTP and REST specifications, `PATCH` and `PUT` methods are used to be for update operations.
In Spring Data REST we can use `PATCH` method and `Content-Type: text/uri-list` Header. Lets add one(or more) comment to our page(blog):

```javascript
curl -X PATCH http://127.0.0.1:7000/blogs/1/comments -H 'Content-Type: text/uri-list'  -d http://127.0.0.1:7000/comments/3
```
Note that when using `Content-Type: text/uri-list` Header, consider each URI shall appear on one and only one line.


**********


### How to add a resource with embedded in-line childes (Composition)?

Some oneToMany relationships is in type of Composition. Composition is used when the life of the child is completely controlled by the parent.
Suppose we want to add some keywords for each page(blog). This relationship is a composition, which means the keywords can not exists if the page destroy.
Spring Data REST Allowed child resources to be embedded in parent resource if the child domain doesn't have repository. Let's add a page(blog) with embedded keywords.

First we add a `KeyWord` entity:

```java
@Entity
@Data
public class KeyWord {

    @Id
    @GeneratedValue(generator = "KeywordIdSeq")
    private Long id;

    @Nationalized
    private String word;

}
```

and the KeyWord should not be exported:

```java
@RepositoryRestResource(exported = false)
public interface KeyWordRepository extends CrudRepository<KeyWord, Long> {
}
```


**********


### How to enable paging and sorting capability on findAll method?

Extend you repository from `PagingAndSortingRepository` instead of 'CrudRepository'. Note that `PagingAndSortingRepository` is sub-interface of `CrudRepository` and you don't loose any of predefined methods. 

```java
@RepositoryRestResource
public interface CommentsRepository extends PagingAndSortingRepository<Comment, Long> {
}
```

Now you can send  `Paging` and `Sorting` data on your `GET` Request and then you get sorted result and paging info (size, totalElement, totalPage, ...) in response payload.


```javascript
curl -X GET 'http://127.0.0.1:7000/comments?size=3&page=0&sort=text,desc'
{
    "_embedded": {
        "comments": [
            {
                "text": "This is comment No 871",
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/comments/2"
                    },
                    "comment": {
                        "href": "http://127.0.0.1:7000/comments/2"
                    }
                }
            },
            {
                "text": "This is comment No 691",
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/comments/1"
                    },
                    "comment": {
                        "href": "http://127.0.0.1:7000/comments/1"
                    }
                }
            },
            {
                "text": "This is comment No 310",
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/comments/3"
                    },
                    "comment": {
                        "href": "http://127.0.0.1:7000/comments/3"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/comments"
        },
        "profile": {
            "href": "http://127.0.0.1:7000/profile/comments"
        }
    },
    "page": {
        "size": 3,
        "totalElements": 3,
        "totalPages": 1,
        "number": 0
    }
}
```

**********

### How to add paging and sorting to custom search methods?

You can easily add paging and sorting capability to your search methods. Just add a `Pagebale` argument to your method signature and change return type of method to `Page<T>`.
We are going to add a `Pagebale` and sortable search method to our comments repository.

```java
@RepositoryRestResource
public interface CommentsRepository extends PagingAndSortingRepository<Comment, Long> {

    Page<Comment> findByTextLike(@Param("text") String text, Pageable pageable);
}
```

then we can use this feature


```javascript
curl -X GET 'http://127.0.0.1:7000/comments/search/findByTextLike?text=This%25&page=0&size=2&sort=text,asc'

{
    "_embedded": {
        "comments": [
            {
                "text": "This is my comment on blog 197",
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/comments/5"
                    },
                    "comment": {
                        "href": "http://127.0.0.1:7000/comments/5"
                    }
                }
            },
            {
                "text": "This is my comment on blog 238",
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/comments/3"
                    },
                    "comment": {
                        "href": "http://127.0.0.1:7000/comments/3"
                    }
                }
            }
        ]
    },
    "page": {
        "size": 2,
        "totalElements": 11,
        "totalPages": 6,
        "number": 0
    }
}
```


**********

### How to add validation to repositories?

It is possible to define validators in Spring Data Rest. These validators can trigger on simultaneous events. The events are `before/after create`, `before/after update`, `before/after delete`.  
The first step to add a validator is create a validator class that implements Spring 'Validator' interface. In the following code we are going to create a validator that validate comment resources before creation.


```java
public class BeforeCreateCommentValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(Comment.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Comment comment = (Comment)o;
        if(comment.getText() == null || comment.getText().isEmpty())
            errors.rejectValue("text", "comment.text.empty");

    }
}
```

As you can see in the code, we checked the text property of incoming comment object. In case of failed validation we should specify a suitable message key (`comment.text.empty`) to be sent back to client.

Then you should register your validator (and the corresponding event) to Spring Data REST.

```java
@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        validatingListener.addValidator("beforeCreate", new BeforeCreateCommentValidator());
    }
}
```

and you should also define your message in `message.properties` file.


```java
comment.text.empty=text is empty.
```

Now we can test our validan using the following request:


```javascript
curl -X POST http://127.0.0.1:7000/comments -H  -H 'Content-Type: application/json' -d '{}'


{
    "errors": [
        {
            "entity": "Comment",
            "property": "text",
            "invalidValue": null,
            "message": "text is empty."
        }
    ]
}

```

**********

### How to use events on a repository?

Spring Data Rest supports 8 types of events in regards to crating, updating, deleting resources. Here is a list of events that we can define for resourcesBeforeCreateEvent:
`BeforeCreateEvent` `AfterCreateEvent` `BeforeSaveEvent` `AfterSaveEvent` `BeforeLinkSaveEvent` `AfterLinkSaveEvent` `BeforeDeleteEvent` `AfterDeleteEvent` 
Suppose we gonna to set a field on creation of a comment resource. In order to add event handler our code may be something like this:

```java
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
```

After adding this code the event handler will be applied on creation of comment resources.

```javascript
curl -X POST http://127.0.0.1:7000/comments -H 'Content-Type: application/json' -d '{"text" : "that is a good one"}'


{
    "text": "that is a good one",
    "submitBy": "dummyUser",
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/comments/1"
        },
        "comment": {
            "href": "http://127.0.0.1:7000/comments/1"
        }
    }
}
```

**********

### How to define a projection for a REST resource?

If you need to change the representation of a resource, projections is the answer. When clients send a GET request in order to fetch resource(es) the Spring Date REST server create result
with respect to projection. Client specify the projection by sending a `projection` parameter along with the request. Here we are goring to create a `NoTitleWithComments` projection for `Page` (Blog) resource.
As the name shows this is a projection that doesn't contain any information about the titles and embedded comments of a page (blog). The following code adds the projection to project:


```java
@Projection(name = "pagesNoTitleWithCommentsProjection", types = {Page.class})
public interface PagesNoTitleWithCommentsProjection {
    String getDescription();
    List<KeyWord> getKeyWords();
    List<Comment> getComments();
}
```

Now clients are able to GET pages (Bloges) in the predefined projecttion.

```javascript
http://127.0.0.1:7000/blogs?projection=pagesNoTitleWithCommentsProjection

{
    "_embedded": {
        "pages": [
            {
                "description": "the awsome fast food",
                "keyWords": [
                    {
                        "word": "eat"
                    },
                    {
                        "word": "drink"
                    }
                ],
                "comments": [
                    {
                        "text": "This is my comment on blog # 268",
                        "submitBy": "dummyUser"
                    }
                ],
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/blogs/1"
                    },
                    "page": {
                        "href": "http://127.0.0.1:7000/blogs/1{?projection}",
                        "templated": true
                    },
                    "comments": {
                        "href": "http://127.0.0.1:7000/blogs/1/comments"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/blogs?projection=pagesNoTitleWithCommentsProjection"
        },
        "profile": {
            "href": "http://127.0.0.1:7000/profile/blogs"
        },
        "search": {
            "href": "http://127.0.0.1:7000/blogs/search"
        }
    }
}

```


if no projection have been sent by client, the server create the query result with default 


```javascript
http://127.0.0.1:7000/blogs
{
    "_embedded": {
        "pages": [
            {
                "title": "Amir Fast Food",
                "description": "the awsome fast food",
                "keyWords": [
                    {
                        "word": "eat"
                    },
                    {
                        "word": "drink"
                    }
                ],
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/blogs/1"
                    },
                    "page": {
                        "href": "http://127.0.0.1:7000/blogs/1{?projection}",
                        "templated": true
                    },
                    "comments": {
                        "href": "http://127.0.0.1:7000/blogs/1/comments"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/blogs"
        },
        "profile": {
            "href": "http://127.0.0.1:7000/profile/blogs"
        },
        "search": {
            "href": "http://127.0.0.1:7000/blogs/search"
        }
    }
}
```

**********

### How to define a nested projection for a REST resource?
In Spring Data REST you can use one projection in another projections and simply create rich data representations. In the following example we are going to create a projection, then we use it in another projection.
First we create a projection for comments named `commentsJustTextProjection`

```java
@Projection(name = "pagesNoTitleWithCommentsProjection", types = {Page.class})
public interface PagesNoTitleWithCommentsProjection {
    String getDescription();
    List<KeyWord> getKeyWords();
    List<Comment> getComments();
}
```

After that, we create `pagesJustTitleAndCommentProjection` projection. This projection has `pagesNoTitleWithCommentsProjection` in itself;

```java
@Projection(name = "pagesJustTitleAndCommentProjection", types = {Page.class})
public interface PagesJustTitleAndCommentProjection {
    String getTitle();
    List<CommentsJustTextProjection> getComments();
}
```

after adding these projections to project, when `pagesJustTitleAndCommentProjection` is used, both projections are applied to the result of request

```javascript

curl -X GET 'http://127.0.0.1:7000/blogs?projection=pagesJustTitleAndCommentProjection'

{
    "_embedded": {
        "pages": [
            {
                "comments": [
                    {
                        "text": "This is my comment on blog # 733",
                        "_links": {
                            "self": {
                                "href": "http://127.0.0.1:7000/comments/1{?projection}",
                                "templated": true
                            }
                        }
                    }
                ],
                "title": "Amir Fast Food",
                "_links": {
                    "self": {
                        "href": "http://127.0.0.1:7000/blogs/1"
                    },
                    "page": {
                        "href": "http://127.0.0.1:7000/blogs/1{?projection}",
                        "templated": true
                    },
                    "comments": {
                        "href": "http://127.0.0.1:7000/blogs/1/comments{?projection}",
                        "templated": true
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://127.0.0.1:7000/blogs?projection=pagesJustTitleAndCommentProjection"
        },
        "profile": {
            "href": "http://127.0.0.1:7000/profile/blogs"
        },
        "search": {
            "href": "http://127.0.0.1:7000/blogs/search"
        }
    }
}

```

**********

### How to define an excerpt for a REST resource?

By using excepts, you can create a projection and set it on a repository. A repository excerpt will be apply on all collection resources of repository.

**********

### How to add enumerations fields to a REST resource?

First create enum:

```java
public enum CommentStatus {
    APPROVED,
    REJECTED,
    PENDING
}
```

Add enum field to entity:

```java@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(generator = "CommentIdSeq")
    private Long id;

    @Nationalized
    private String text;

    private String submitBy;

    private CommentStatus status;

}
```

add enum translation values in `rest-messages.properties` file

```java
ir.brochure.SpringDataRestStepByStep.entity.CommentStatus.APPROVED=Approved
ir.brochure.SpringDataRestStepByStep.entity.CommentStatus.REJECTED=Rejected
ir.brochure.SpringDataRestStepByStep.entity.CommentStatus.PENDING=Pending
```


enable enum translation in `application.properties` file

```java
spring.data.rest.enable-enum-translation=true
```

now you can use translations in requests and see translations in response:

```javascript
curl -X POST http://127.0.0.1:7000/comments -H 'Content-Type: application/json'   -d '{	"text" : "This is my comment on blog # 588","status" : "Approved"}'
```

**********

### How to use ETag header in Spring Data REST?

By usnig ETag header we can check the version of resource. This can be useful in situations when clients doesn't know whether they have the last  version of resource or not.
Spring Data REST automatically adds ETag header to entities if they have `@Version` annotated field:

```java

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(generator = "CommentIdSeq")
    private Long id;

    @Nationalized
    private String text;

    private String submitBy;

    private CommentStatus status;

    @Version
    private Long version;
}

```

Suppose client A get comment resource (id = 1)

```javascript
curl -i -X GET   http://127.0.0.1:7000/comments/1

ETag: "0"
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 15 Jun 2019 12:11:01 GMT

{
  "text" : "This is my comment on blog ",
  "submitBy" : "dummyUser",
  "status" : "Approved",
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:7000/comments/1"
    },
    "comment" : {
      "href" : "http://127.0.0.1:7000/comments/1{?projection}",
      "templated" : true
    }
  }
}

```


Client B  update this resource

```javascript
curl -i -X PATCH http://127.0.0.1:7000/comments/1  -H 'Content-Type: application/json' -d '{"text" : "my comment is edited"}'

ETag: "1"
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
Content-Type: application/hal+json;charset=UTF-8
Transfer-Encoding: chunked
Date: Sat, 15 Jun 2019 12:13:47 GMT

{
  "text" : "my comment is edited",
  "submitBy" : "dummyUser",
  "status" : "Approved",
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:7000/comments/1"
    },
    "comment" : {
      "href" : "http://127.0.0.1:7000/comments/1{?projection}",
      "templated" : true
    }
  }
}


```

Now client A doesn't have last version of the resource. Client A wants to update the resource if he has the last version. In this situations `If-Match` header comes to rescue, 
so client A fill `If-Match` with the last version (ETag) of resource that he/she has and send the update request.

``javascript

curl -X PATCH http://127.0.0.1:7000/comments/1   -H 'Content-Type: application/json'  -H 'If-Match: 0' -d '{"text" : "my comment is edited by Client A"}' -sw %{http_code}
412
```

as you can client A got `412 Precondition Failed` response code and the update request failed, because he/she doesn't have last version of the entity.


 




