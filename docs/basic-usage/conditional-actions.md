# Conditional Actions

The `if...` family of methods enable us to run some code on the wrapped success/failure value. Before _Result_, we would wrap exception-throwing `foobar` method invocation inside a `try` block so that errors can be handled inside a `catch` block:

```java
try {
    final String result = foobar();
    this.commit(result);
} catch(SomeException problem) {
    this.rollback(problem);
}
```

Let's now look at how the above code could be refactored if method `foobar` returned a _Result_ object instead of throwing an exception:

```java
final Result<String, SomeFailure> result = foobar();
result.ifSuccessOrElse(this::commit, this::rollback);
```

The first action passed to \[`ifSuccessOrElse()`\]\[IF\_SUCCESS\_OR\_ELSE\] will be performed if `foobar` succeeded; otherwise the second one will.

The above example is not only shorter but also faster. We can make it even shorter by chaining methods in typical functional programming style:

```java
foobar().ifSuccessOrElse(this::commit, this::rollback);
```

There are other methods \[`ifSuccess()`\]\[IF\_SUCCESS\] and \[`ifFailure()`\]\[IF\_FAILURE\] to handle either one of the success/ failure cases only:

```java
foobar(123)
    .ifSuccess(this::commit) // commits only if the result is success
    .ifFailure(this::rollback); // rolls back only if the result is failure
```

