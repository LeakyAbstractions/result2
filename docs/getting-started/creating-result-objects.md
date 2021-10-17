---
description: Creating Result Objects
---

# Instantiating Result

There are several ways of creating `Result` objects.

### Successful Result <a href="creating-result-objects" id="creating-result-objects"></a>

To create a successful result, we simply need to use static method `Results.success()`:

```java
@Test
void should_be_success() {
    // When
    final Result<Integer, ?> result = Results.success(123);
    // Then
    assertThat(result.isSuccess()).isTrue();
}
```

Note that we can use methods `isSuccess()` or `isFailure()` to check if the result was\
successful or not.

### Failed Result

On the other hand, if we want to create a failed result, we can use static method `Results.failure()`:

```java
@Test
void should_be_failure() {
    // When
    final Result<?, String> result = Results.failure("The operation failed");
    // Then
    assertThat(result.isSuccess()).isFalse();
}
```

### Result Based on Nullable Value

We can use static method `Results.ofNullable()` to create results that depend on a possibly-null value:

```java
@Test
void should_not_be_failure() {
    // Given
    final String nullable = "Hello world!";
    // When
    final Result<String, Integer> result = Results.ofOptional(nullable, 0);
    // Then
    assertThat(result.isSuccess()).isTrue();
}
```

### Result Based on Optional Value

We can also use static method `Results.ofOptional()` to create results that depend on an optional value:

```java
@Test
void should_not_be_success() {
    // Given
    final Optional<String> optional = Optional.empty();
    // When
    final Result<String, Integer> result = Results.ofOptional(optional, -1);
    // Then
    assertThat(result.isFailure()).isTrue();
}
```

### Result Based on Callable Value

And sometimes it might come in handy to wrap actual thrown exceptions inside a result object via static method `Results.ofCallable()`:

```java
@Test
void should_wrap_exception() {
    // Given
    final Callable<String> callable = () -> { throw new RuntimeException("Whoops!") };
    // When
    final Result<String, Exception> result = Results.ofCallable(callable);
    // Then
    assertThat(result.isFailure()).isTrue();
}
```
