A step by step walk-though guide for developing RESTfull web services using Spring Data Rest.  

Spring Data REST is a very nice tool to develop HTTP Resources with minimum boilerplate code.
Spring Data REST is a subproject of Spring data. Spring Data Rest Analyze your repositories and expose them as REST Endpoint.


[How to expose an HTTP REST resource?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-expose-an-http-rest-resource)

[How to customize repository access path?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-customize-repository-access-path)

[How to use POST method on a repository?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-use-post-method-on-a-repository)

[How to use GET and HEAD method on a repository?](https://github.com/amirtvk/SpringDataRest_StepByStep#how-to-use-get-and-head-method-on-a-repository)


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
Note that detection of repositories in code is done based on `RepositoryDiscoveryStrategies`

we can access to HTTP resource:
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

### How to use POST method on a repository?

A POST method is used for create a new resource in repository. for example if you want to create a new blog (page entity) just send a POST request including resource attributes in request body

```
curl -X POST http://127.0.0.1:7000/blogs -H 'Content-Type: application/json' -d '{	"title" : "Amir Super Market",	"description" : "the best market ever"}'
```
After running this command we got a `201` response code which means the resource was `created`


### How to use GET and HEAD method for a repository?

Get method get one or list of resources. if you want to get list of all resources in a repository simply sent a GET request to the repository.

```json
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
As you can see there are two resources in `pages` tag in response of GET request.

If you like to get details about just one resource you have to send a GET request on the resource URI (`_links.self.href`)

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

HEAD method is used to check the availability of a resource. 

```javascript
curl -X HEAD http://127.0.0.1:7000/blogs/1
```
when you run this command, if a resource which has `identifier = 1` exists you will got a 204 response code `No Content` else the response code will be 404 `Not Found`














