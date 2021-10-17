# Result Library for Java

### Result Library for Java <a href="result-library-for-java" id="result-library-for-java"></a>

The purpose of this library is to provide a type-safe encapsulation of operation results that may have succeeded or failed, instead of throwing exceptions.

If you like Optional but feel that it sometimes falls too short, you’ll love Result.

The best way to think of Result is as a super-powered version of Optional. The only difference is that whereas Optional may contain a successful value or express the absence of a value, Result contains either a successful value or a failure value that explains what went wrong.

`Result` objects have methods equivalent to those in `Optional`, plus a few more to handle failure values.

| Optional          | Result            |
| ----------------- | ----------------- |
| `isPresent`       | `isSuccess`       |
| `isEmpty`         | `isFailure`       |
| `get`             |                   |
| `orElse`          | `orElse`          |
| `orElseGet`       | `orElseMap`       |
| `orElseThrow`     |                   |
|                   | `optional`        |
|                   | `optionalFailure` |
| `stream`          | `stream`          |
|                   | `streamFailure`   |
| `ifPresent`       | `ifSuccess`       |
|                   | `ifFailure`       |
| `ifPresentOrElse` | `ifSuccessOrElse` |
| `filter`          | `filter`          |
| `map`             | `mapSuccess`      |
|                   | `mapFailure`      |
|                   | `map`             |
| `flatMap`         | `flatMapSuccess`  |
| `or`              | `flatMapFailure`  |
|                   | `flatMap`         |

***

### Adding Result to Your Build <a href="adding-result-to-your-build" id="adding-result-to-your-build"></a>

The library requires JDK 1.8 or higher. Other than that, it has no external dependencies and it is very lightweight. Adding it to your build should be very easy.

Artifact coordinates:

* Group ID: `com.leakyabstractions`
* Artifact ID: `result`
* Version: `0.5.0.0`

To add a dependency on Result using [**Maven**](https://maven.apache.org), use the following:

```
<dependencies>
    <dependency>
        <groupId>com.leakyabstractions</groupId>
        <artifactId>result</artifactId>
        <version>0.5.0.0</version>
        <type>pom</type>
    </dependency>
</dependencies>
```

To add the dependency using [**Gradle**](https://gradle.org), if you are building an application that will use Result internally:

```
dependencies {
    implementation 'com.leakyabstractions:result:0.5.0.0'
}
```

If you are building a library that will use Result type in its public API, you should use instead:

```
dependencies {
    api 'com.leakyabstractions:result:0.5.0.0'
}
```

For more information on when to use api and implementation, read the [Gradle documentation on API and implementation separation](https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_separation).

### Creating Result Objects <a href="creating-result-objects" id="creating-result-objects"></a>

There are several ways of creating Result objects.

To create a successful result, we simply need to use static method [`Results.success()`](broken-reference):

```
@Test
void should_be_success() {
    // When
    final Result<Integer, ?> result = Results.success(123);
    // Then
    assertThat(result.isSuccess()).isTrue();
}
```

Note that we can use methods [`isSuccess()`](broken-reference) or [`isFailure()`](broken-reference) to check if the result was successful or not.

On the other hand, if we want to create a failed result, we can use static method [`Results.failure()`](broken-reference):

```
@Test
void should_be_failure() {
    // When
    final Result<?, String> result = Results.failure("The operation failed");
    // Then
    assertThat(result.isSuccess()).isFalse();
}
```

We can use static method [`Results.ofNullable()`](broken-reference) to create results that depend on a possibly-null value:

```
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

We can also use static method [`Results.ofOptional()`](broken-reference) to create results that depend on an optional value:

```
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

And sometimes it might come in handy to wrap actual thrown exceptions inside a result object via static method [`Results.ofCallable()`](broken-reference):

```
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

There’s also a way to encapsulate expensive operations that can be entirely omitted (as an optimization) if there’s no actual need to examine the result. To create a _lazy_ result we need to use static method [`Results.lazy()`](broken-reference):

```
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

```
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

### Conditional Actions <a href="conditional-actions" id="conditional-actions"></a>

The if… family of methods enable us to run some code on the wrapped success/failure value. Before Result, we would wrap exception-throwing foobar method invocation inside a try block so that errors can be handled inside a catch block:

```
try {
    final String result = foobar();
    this.commit(result);
} catch(SomeException problem) {
    this.rollback(problem);
}
```

Let’s now look at how the above code could be refactored if method foobar returned a Result object instead of throwing an exception:

```
final Result<String, SomeFailure> result = foobar();
result.ifSuccessOrElse(this::commit, this::rollback);
```

The first action passed to [`ifSuccessOrElse()`](broken-reference) will be performed if foobar succeeded; otherwise the second one will.

The above example is not only shorter but also faster. We can make it even shorter by chaining methods in typical functional programming style:

```
foobar().ifSuccessOrElse(this::commit, this::rollback);
```

There are other methods [`ifSuccess()`](broken-reference) and [`ifFailure()`](broken-reference) to handle either one of the success/ failure cases only:

```
foobar(123)
    .ifSuccess(this::commit) // commits only if the result is success
    .ifFailure(this::rollback); // rolls back only if the result is failure
```

### Unwrapping Values <a href="unwrapping-values" id="unwrapping-values"></a>

The [`Optional::orElse`](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/Optional.html#orElse\(T\)) method is used to retrieve the value wrapped inside an Optional instance, or a _default value_ in case the Optional is empty.

Similarly, you can use [`orElse()`](broken-reference) to obtain the success value held by a Result object; or a _default success value_ in case the result is failed.

```
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

The [`orElseMap()`](broken-reference) method is similar to orElse(). However, instead of taking a value to return if the Result object is failed, it takes a mapping function, which would be applied to the failure value to produce an alternative success value:

```
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

### Filtering Success Values <a href="filtering-success-values" id="filtering-success-values"></a>

We can run an inline test on our wrapped success value with the [`filter()`](broken-reference) method. It takes a predicate and a mapping function as arguments and returns a Result object:

* If it is a failed result, or it is a successful result whose success value passes testing by the predicate then the Result is returned as-is.
* If the predicate returns false then the mapping function will be applied to the success value to produce a failure value that will be wrapped in a new failed result.

```
@Test
void should_pass_test() {
    // Given
    final Result<Integer, String> result = success(-1);
    final Predicate<Integer> isPositive = i -> i >= 0;
    // When
    final Result<Integer, String> filtered = result.filter(isPositive, i -> "Negative number");
    // Then
    assertThat(filtered.isFailure()).isTrue();
}
```

The filter method is normally used to reject wrapped success values based on a predefined rule.

### Transforming Values <a href="transforming-values" id="transforming-values"></a>

In the previous section, we looked at how to reject or accept a success value based on a filter.

We can also transform success/failure values held by Result objects with the map… family of methods:

```
@Test
void should_return_string_length() {
    // Given
    final Result<String, Integer> result = success("ABCD");
    // When
    final Result<Integer, String> mapped = result.mapSuccess(String::length);
    // Then
    assertThat(mapped.optional()).contains(4);
}
```

In this example, we wrap a String inside a Result object and use its [`mapSuccess()`](broken-reference) method to manipulate it (here we calculate its length). Note that we can specify the action as a method reference, or a lambda. In any case, the result of this action gets wrapped inside a new Result object. And then we call the appropriate method on the returned result to retrieve its value.

There is another [`map()`](broken-reference) method to transform either success/failure value at once:

```
@Test
void should_return_upper_case() {
    // Given
    final Result<String, String> result = success("Hello World!");
    // When
    final Result<String, String> mapped = result
        .map(String::toUpperCase, String::toLowerCase);
    // Then
    assertThat(mapped.optional()).contains("HELLO WORLD!");
}

@Test
void should_return_lower_case() {
    // Given
    final Result<String, String> result = failure("Hello World!");
    // When
    final Result<String, String> mapped = result
        .map(String::toUpperCase, String::toLowerCase);
    // Then
    assertThat(mapped.optionalFailure()).contains("hello world!");
}
```

And the [`mapFailure()`](broken-reference) method allows us to transform failure values only:

```
@Test
void should_return_is_empty() {
    // Given
    final Result<Integer, String> result = failure("");
    // When
    final Result<Integer, Boolean> mapped = result.mapFailure(String::isEmpty);
    // Then
    assertThat(mapped.optionalFailure().orElse(false)).isTrue();
}
```

Just like the map… methods, we also have the flatMap… family of methods as an alternative for transforming values. The difference is that map… methods don’t alter the success/failure state of the result, whereas with flatMap… ones, you can start with a successful result and end up with a failed one, and _vice versa_.

Previously, we created simple String and Integer objects for wrapping in a Result instance. However, frequently, we will receive these objects as we invoke third-party methods.

To get a clearer picture of the difference, let’s have a look at a User object that takes a name and a boolean flag that determines if the user has custom configuration. It also has a method getCustomConfigPath which returns a Result containing either the path to the user configuration file, or a Problem object describing why the path cannot be obtained:

```
class User {

    final String name;
    final boolean hasCustomConfig;

    public User(String name, boolean hasCustomConfig){
        this.name = name;
        this.hasCustomConfig = hasCustomConfig;
    }

    public Result<String, Problem> getCustomConfigPath() {
        if (!this.hasCustomConfig) {
            return failure(new UserProblem("User does not have custom configuration"));
        }
        return success("/config/" + this.name + ".cfg");
    }
}
```

Now suppose we have a method openFile which checks if a given file exists and returns a result containing the file object or a Problem object explaining why the file cannot be retrieved:

```
Result<File, Problem> openFile(String path) {
    final File file = new File(path);
    return file.exists() ? success(file) : failure(new FileProblem("File does not exist"));
}
```

If we wanted to obtain the file path from the user _and then_ invoke the above method to get the file object, we could use [`flatMapSuccess()`](broken-reference) to fluently transform one result into another:

```
@Test
void should_contain_file() {
    // Given
    final User user = new User("Rachel", true);
    // When
    final Result<File, Problem> result = user.getCustomConfigPath()
        .flatMapSuccess(this::openFile);
    // Then
    assertThat(result.orElse(null)).isAbsolute();
}

@Test
void should_contain_user_problem() {
    // Given
    final User user = new User("Monica", false);
    // When
    final Result<File, Problem> result = user.getCustomConfigPath()
        .flatMapSuccess(this::openFile);
    // Then
    assertThat(result.optionalFailure()).containsInstanceOf(UserProblem.class);
}

@Test
void should_contain_file_problem() {
    // Given
    final User user = new User("../../wrong//path/", true);
    // When
    final Result<File, Problem> result = user.getCustomConfigPath()
        .flatMapSuccess(this::openFile);
    // Then
    assertThat(result.optionalFailure()).containsInstanceOf(FileProblem.class);
}
```

There is another [`flatMap()`](broken-reference) method to transform either success/failure values at once:

```
@Test
void should_contain_123() {
    // Given
    final User user = new User("Phoebe", false);
    // When
    final Result<File, Integer> result = user.getCustomConfigPath()
        .flatMap(this::openFile, f -> 123);
    // Then
    assertThat(result.optionalFailure()).contains(123);
}
```

And the [`flatMapFailure()`](broken-reference) method allows us to transform failure values only:

```
@Test
void should_contain_error() {
    // Given
    final User user = new User("Joey", false);
    // When
    final Result<String, String> result = user.getCustomConfigPath()
        .flatMapFailure(f -> "error");
    // Then
    assertThat(result.optionalFailure()).contains("error");
}
```

### Asserting Result objects <a href="asserting-result-objects" id="asserting-result-objects"></a>

You can use fluent assertions (based on [AssertJ](https://assertj.github.io)) for Result objects in your unit tests.

To add a dependency on Result assertions using **Maven**, use the following:

```
<dependency>
    <groupId>com.leakyabstractions</groupId>
    <artifactId>result-assertj</artifactId>
    <version>0.5.0.0</version>
    <scope>test</scope>
</dependency>
```

To add a dependency using **Gradle**:

```
dependencies {
    testImplementation 'com.leakyabstractions:result-assertj:0.5.0.0'
}
```

This allows you to use Result assertions in your tests via assertThat:

```
import static com.leakyabstractions.result.assertj.ResultAssertions.assertThat;

@Test
public void should_pass() {
    // Given
    final int number = someMethodReturningInt();
    // When
    final Result<String, Integer> result = someMethodReturningResult(number);
    // Then
    assertThat(number).isZero();
    assertThat(result).hasSuccess("OK");
}
```

If, for some reason, you cannot statically import static method ResultAssertions.assertThat you can use static method ResultAssert.assertThatResult instead:

```
import static com.leakyabstractions.result.assertj.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;

@Test
public void should_pass_too() {
    // Given
    final int number = anotherMethodReturningInt();
    // When
    final Result<String, Integer> result = anotherMethodReturningResult(number);
    // Then
    assertThat(number).isOne();
    assertThatResult(result).hasFailure(1);
}
```

### Releases <a href="releases" id="releases"></a>

This library adheres to [Pragmatic Versioning](https://pragver.github.io).

Artifacts are available in [Maven Central](https://search.maven.org/artifact/com.leakyabstractions/result).

### Javadoc <a href="javadoc" id="javadoc"></a>

Here’s the full [Result API documentation](.gitbook/assets/0.5.0).

### Looking for Support? <a href="looking-for-support" id="looking-for-support"></a>

We’d love to help. Check out the [support guidelines](broken-reference).

### Contributions Welcome <a href="contributions-welcome" id="contributions-welcome"></a>

If you’d like to contribute to this project, please [start here](broken-reference).

### Code of Conduct <a href="code-of-conduct" id="code-of-conduct"></a>

This project is governed by the [Contributor Covenant Code of Conduct](broken-reference). By participating, you are expected to uphold this code.
