# Creating Result Objects

There are several ways of creating _Result_ objects.

## Successful Results

To create a successful result, we simply need to use the static method `Results.success`:

```java
@Test
void should_be_success() {
    // When
    final Result<Integer, ?> result = Results.success(123);
    // Then
    assertThat(result.isSuccess()).isTrue();
}
```

Note that we can use methods `isSuccess` or `isFailure` to check if the result was successful or not.

## Failed Results

On the other hand, if we want to create a failed result, we can use the static method `Results.failure`:

```java
@Test
void should_not_be_success() {
    // When
    final Result<?, String> result = Results.failure("The operation failed");
    // Then
    assertThat(result.isSuccess()).isFalse();
}
```

## Optional to Result

We can also use the static method `Results.ofOptional` to create results that depend on an optional value:

```java
@Test
void should_be_failure() {
    // Given
    final Optional<String> optional = Optional.empty();
    // When
    final Result<String, Void> result = Results.ofOptional(optional);
    // Then
    assertThat(result.isFailure()).isTrue();
}
```

## Wrapping Exceptions

Sometimes it might come in handy to encapsulate the actual thrown exceptions inside a result object via the static method `Results.wrap`:

```java
@Test
void should_be_failure_too() {
    // Given
    final Callable<String> callable = () -> { throw new RuntimeException("Whoops!") };
    // When
    final Result<String, Exception> result = Results.wrap(callable);
    // Then
    assertThat(result.isFailure()).isTrue();
}
```

## Lazy results

There's also a way to encapsulate expensive operations that can be entirely omitted \(as an optimization\) if there's no actual need to examine the result. To create a _lazy_ result we need to use the static method `Results.lazy`:

```java
    Result<String, Void> expensiveCalculation(AtomicLong timesExecuted) {
        timesExecuted.getAndIncrement();
        return Results.success("HELLO");
    }

    @Test
    void should_not_execute_expensive_action() {
        final AtomicLong timesExecuted = new AtomicLong();
        // Given
        final Result<String, Void> lazy = Results
                .lazy(() -> this.expensiveCalculation(timesExecuted));
        // When
        final Result<Integer, Void> transformed = lazy.mapSuccess(String::length);
        // Then
        assertThat(transformed).isNotNull();
        assertThat(timesExecuted).hasValue(0);
    }
```

Lazy results can be manipulated just like any other result; they will try to defer the invocation of the given supplier as long as possible. For example, when we actually try to determine if the operation succeeded or failed.

```java
    @Test
    void should_execute_expensive_action() {
        final AtomicLong timesExecuted = new AtomicLong();
        // Given
        final Result<String, Void> lazy = Results
                .lazy(() -> this.expensiveCalculation(timesExecuted));
        // When
        final Result<Integer, Void> transformed = lazy.mapSuccess(String::length);
        final boolean success = transformed.isSuccess();
        // Then
        assertThat(success).isTrue();
        assertThat(timesExecuted).hasValue(1);
    }
```

