---
description: 🔀 Handling success and failure
---

# 🔀 Conditional Actions

The `if...` family of methods enables us to run some code on the wrapped success/failure value.

### Handling Exceptions

Before `Result`, we would wrap exception-throwing `foobar` method invocation inside a `try` block so that errors can be handled inside a `catch` block:

```java
try {
    final String result = foobar();
    this.commit(result);
} catch(SomeException problem) {
    this.rollback(problem);
}
```

### Handling Results

Let's now look at how the above code could be refactored if method `foobar` returned a `Result` object instead of throwing an exception:

```java
final Result<String, SomeFailure> result = foobar();
result.ifSuccessOrElse(this::commit, this::rollback);
```

The first action passed to `ifSuccessOrElse()` will be performed if foobar succeeded;\
otherwise, the second one will.

The above example is not only shorter but also faster. We can make it even shorter by chaining methods in typical functional programming style:

```java
foobar().ifSuccessOrElse(this::commit, this::rollback);
```

{% hint style="info" %}
There are other methods `ifSuccess()` and `ifFailure()` to handle either one of the success/failure cases only:

```java
foobar()
    .ifSuccess(this::commit) // commits only if the result is success
    .ifFailure(this::rollback); // rolls back only if the result is failure
```
{% endhint %}

###  <a href="unwrapping-values" id="unwrapping-values"></a>
