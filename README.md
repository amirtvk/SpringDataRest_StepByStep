A step by step walk-though guide for developing RESTfull web services using Spring Data Rest.  

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


### How to use DELETE method on a repository?

`DELETE` method is use for delete a resource. In order to delete a resource send a `DELETE` request to resource URI.

```
curl -X DELETE http://127.0.0.1:7000/blogs/4
```
You will got a `204` `No Content` response code if server can find and delete the resource.


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




### How clients can discover resources based on HATEOS?

according to `HATEOS`, all resources should be discoverable by client. Spring Data REST supports `HATEOS` through `HAL`. In Returned document there is a `_links` tag that includes self and related links to discover other resources.


### How to enable HAL-Browser?

HAL-Browser is a nice web application for exploring REST APIs. By adding its dependency to the project class path, HAL-Browser will automatically added to root URL of server. Open a browser and go to

 `http://127.0.0.1:7000` 

![alt text](https://github.com/amirtvk/SpringDataRest_StepByStep/raw/master/img/halbrowser.png "HAL Browser")












