# Unwrapping Values

The method [`Optional::orElse`](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/Optional.html#orElse%28T%29)  is used to retrieve the value wrapped inside an `Optional` instance, or a _default value_ in case the optional is empty.

## Success Value

Similarly, you can use `orElse` to obtain the success value held by a _Result_ object, or a default success value in case the result is failed.

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

The `orElseMap` _method is similar to `orElse`._ However, instead of taking a value to return if the Result object is failed, it takes a mapping function, which would be applied to the failure value to produce an alternative success value:

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

The `orElseThrow` methods follow from `orElse` and `orElseMap` and add a new approach for handling a failed result.

Instead of returning a default value, they throw an exception. If you provide a mapping function, you can transform the failure value to the appropriate exception to be thrown. If you don't, then [NoSuchElementException](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/NoSuchElementException.html) will be thrown.

```java
@Test
void should_throw_exception() {
    // Given
    final Result<Integer, String> result = failure("Could not compute result");
    // When
    final ThrowingCallable callable = () -> result.orElseThrow();
    // Then
    assertThatThrownBy(callable).isInstanceOf(NoSuchElementException.class);
}

@Test
void should_return_success_value() {
    // Given
    final Result<Integer, String> result = success(0);
    // When
    final Integer number = result.orElseThrow(IllegalArgumentException::new);
    // Then
    assertThat(number).isZero();
}
```

## Failure Value

The method `getFailureOrElseThrow` is the counterpart of `orElseThrow`; it will return the failure value unless the result is successful:

```java
@Test
void should_return_failure_value() {
    // Given
    final Result<Integer, String> result = failure("NETWORK PROBLEM");
    // When
    final String error = result.getFailureOrElseThrow();
    // Then
    assertThat(error).isEqualTo("NETWORK PROBLEM");
}

@Test
void should_throw_exception() {
    // Given
    final Result<Integer, String> result = success(0);
    // When
    final ThrowingCallable callable = () -> result.getFailureOrElseThrow();
    // Then
    assertThatThrownBy(callable).isInstanceOf(NoSuchElementException.class);
}
```

