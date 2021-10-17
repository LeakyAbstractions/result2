# Unwrapping Values

The [`Optional::orElse`](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/Optional.html#orElse\(T) method is used to retrieve the value wrapped inside an `Optional` instance, or a _default value_ in case the `Optional` is empty.

### Default Success Value

Similarly, you can use `orElse()` to obtain the success value held by a `Result` object; or a _default success value_ in case the result is failed.

```java
@Test
void should_return_the_success_value() {
    // Given
    final Result<String, Integer> result = success("HELLO");
    // When
    final String greeting = result.orElse("HI");
    // Then
    assertThat(greeting).isEqualTo("HELLO");
}

@Test
void should_return_the_default_value() {
    // Given
    final Result<String, Integer> result = failure(1024);
    // When
    final String greeting = result.orElse("HI");
    // Then
    assertThat(greeting).isEqualTo("HI");
}
```

### Alternative Success Value

The `orElseMap()` method is similar to `orElse()`. However, instead of taking a value to return if\
the `Result` object is failed, it takes a mapping function, which would be applied to the failure value to produce an alternative success value:

```java
@Test
void should_map_the_failure_value() {
    // Given
    final Result<String, Boolean> result = failure(false);
    // When
    final String greeting = result.orElseMap(s -> s ? "HI" : "HOWDY");
    // Then
    assertThat(text).isEqualTo("HOWDY");
}
```

### Optional Success/Failure Value

Finally, the methods `optional()` and `optionalFailure()` can be used to wrap success/failure values held by a `Result` object in an `Optional` object.

```java
@Test
void success_should_be_present() {
    // Given
    final Result<String, Integer> result = success("HI");
    // When
    final Optional<String> optional = result.optional();
    // Then
    assertThat(optional).isPresent();
}

@Test
void failure_should_be_empty() {
    // Given
    final Result<String, Integer> result = success("HOWDY");
    // When
    final Optional<Integer> optional = result.optionalFailure();
    // Then
    assertThat(optional).isEmpty();
}
```
