# trampoline-data-web

Extension module for [trampoline-data](../trampoline-data) which adds components for the web-layer.

## FindById annotation

Adding `@FindById` on a method parameter inside a request mapping makes it possible to easily fetch entities from the database.

Usually you would have invoke a service to find an entity by id such as:

```java
@GetMapping("/{id}")
public BlogPostDto findById(@PathVariable int id) {
	BlogPost blogPost = blogPostService.findById(id);
    return this.blogPostMapper.toDto(blogPost);
}
``` 

With `@FindById` you can decrease the amount of code you need to write:

```java
@GetMapping("/{blogPost}")
public BlogPostDto findById(@FindById BlogPost blogPost) {
    return this.blogPostMapper.toDto(blogPost);
}
```

This annotation does not break other modules such as [trampoline-security-abac](../trampoline-security-abac).

By default it maps the path variable to `UUID`. You can change this by implementing a custom `PrimaryKeyMapper`.

