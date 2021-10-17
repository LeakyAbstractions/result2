# Index

***

title: Result Library for Java\
description: A Java library to handle success and failure without exceptions

### image: /result-banner-centered.png <a href="image-result-banner-centered-png" id="image-result-banner-centered-png"></a>

## Result Library for Java <a href="result-library-for-java" id="result-library-for-java"></a>

The purpose of this library is to provide a type-safe encapsulation of operation results that may have succeeded or\
failed, instead of throwing exceptions.

If you like Optional but feel that it sometimes falls too short, you'll love Result.

The best way to think of Result is as a super-powered version of Optional. The only difference is that\
whereas Optional may contain a successful value or express the absence of a value, Result contains\
either a successful value or a failure value that explains what went wrong.

\
`Result` objects have methods equivalent to those in\
`Optional`, plus a few more to handle failure values.\
\
\
\| Optional | Result |\
\|-------------------------|-------------------------|\
\| `isPresent` | `isSuccess` |\
\| `isEmpty` | `isFailure` |\
\| `get` | |\
\| `orElse` | `orElse` |\
\| `orElseGet` | `orElseMap` |\
\| `orElseThrow` | |\
\| | `optional` |\
\| | `optionalFailure` |\
\| `stream` | `stream` |\
\| | `streamFailure` |\
\| `ifPresent` | `ifSuccess` |\
\| | `ifFailure` |\
\| `ifPresentOrElse` | `ifSuccessOrElse` |\
\| `filter` | `filter` |\
\| `map` | `mapSuccess` |\
\| | `mapFailure` |\
\| | `map` |\
\| `flatMap` | `flatMapSuccess` |\
\| `or` | `flatMapFailure` |\
\| | `flatMap` |\
\
\


***



### Unwrapping Values <a href="creating-result-objects" id="creating-result-objects"></a>

The [`Optional::orElse`](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/Optional.html#orElse\(T))\
method is used to retrieve the value wrapped inside an Optional instance, or a _default value_ in case the

Optional is empty.

Similarly, you can use \[`orElse()`]\[OR_ELSE] to obtain the success value held by a Result object; or a \_default_\
_success value_ in case the result is failed.

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

The \[`orElseMap()`]\[OR_ELSE_MAP] method is similar to orElse(). However, instead of taking a value to return if\
the Result object is failed, it takes a mapping function, which would be applied to the failure value to\
produce an alternative success value:

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

Finally, the methods \[`optional()`]\[OPTIONAL] and \[`optionalFailure()`]\[OPTIONAL_FAILURE] can be used to wrap success/\
failure values held by a Result object in an Optional object.

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

### Filtering Success Values <a href="filtering-success-values" id="filtering-success-values"></a>

We can run an inline test on our wrapped success value with the \[`filter()`]\[FILTER] method. It takes a predicate and a\
mapping function as arguments and returns a Result object:

* If it is a failed result, or it is a successful result whose success value passes testing by the predicate then the\
  Result is returned as-is.
* If the predicate returns false then the mapping function will be applied to the success value to produce a\
  failure value that will be wrapped in a new failed result.

```java
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

We can also transform success/failure values held by Result objects with the map... family of methods:

```java
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

In this example, we wrap a String inside a Result object and use its \[`mapSuccess()`]\[MAP_SUCCESS]\
method to manipulate it (here we calculate its length). Note that we can specify the action as a method reference, or a\
lambda. In any case, the result of this action gets wrapped inside a new Result object. And then we call the\
appropriate method on the returned result to retrieve its value.

There is another \[`map()`]\[MAP] method to transform either success/failure value at once:

```java
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

And the \[`mapFailure()`]\[MAP_FAILURE] method allows us to transform failure values only:

```java
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

Just like the map... methods, we also have the flatMap... family of methods as an alternative for\
transforming values. The difference is that map... methods don't alter the success/failure state of the result,\
whereas with flatMap... ones, you can start with a successful result and end up with a failed one, and _vice_\
_versa_.

Previously, we created simple String and Integer objects for wrapping in a Result instance.\
However, frequently, we will receive these objects as we invoke third-party methods.

To get a clearer picture of the difference, let's have a look at a User object that takes a name and a\
boolean flag that determines if the user has custom configuration. It also has a method getCustomConfigPath\
which returns a Result containing either the path to the user configuration file, or a Problem object\
describing why the path cannot be obtained:

```java
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

Now suppose we have a method openFile which checks if a given file exists and returns a result containing the\
file object or a Problem object explaining why the file cannot be retrieved:

```java
Result<File, Problem> openFile(String path) {
    final File file = new File(path);
    return file.exists() ? success(file) : failure(new FileProblem("File does not exist"));
}
```

If we wanted to obtain the file path from the user _and then_ invoke the above method to get the file object, we could\
use \[`flatMapSuccess()`]\[FLATMAP_SUCCESS] to fluently transform one result into another:

```java
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

There is another \[`flatMap()`]\[FLATMAP] method to transform either success/failure values at once:

```java
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

And the \[`flatMapFailure()`]\[FLATMAP_FAILURE] method allows us to transform failure values only:

```java
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

```xml
<dependency>
    <groupId>com.leakyabstractions</groupId>
    <artifactId>result-assertj</artifactId>
    <version>{{ site.current_version }}</version>
    <scope>test</scope>
</dependency>
```

To add a dependency using **Gradle**:

```
dependencies {
    testImplementation 'com.leakyabstractions:result-assertj:{{ site.current_version }}'
}
```

This allows you to use Result assertions in your tests via assertThat:

```java
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

If, for some reason, you cannot statically import static method ResultAssertions.assertThat you can use static\
method ResultAssert.assertThatResult instead:

```java
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

Here's the full\
Result API documentation.

### Looking for Support? <a href="looking-for-support" id="looking-for-support"></a>

We'd love to help. Check out the support guidelines.

### Contributions Welcome <a href="contributions-welcome" id="contributions-welcome"></a>

If you'd like to contribute to this project, please start here.

### Code of Conduct <a href="code-of-conduct" id="code-of-conduct"></a>

This project is governed by the Contributor Covenant Code of Conduct. By participating, you are\
expected to uphold this code.

\[NEW_SUCCESS]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Results.html#success-S-\
\[NEW_FAILURE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Results.html#failure-F-\
\[OF_NULLABLE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Results.html#ofNullable-S-F-\
\[OF_OPTIONAL]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Results.html#ofOptional-java.util.Optional-F-\
\[OF_CALLABLE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Results.html#ofCallable-java.util.concurrent.Callable-\
\[LAZY]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Results.html#lazy-java.util.function.Supplier-\
\[IS_SUCCESS]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#isSuccess--\
\[IS_FAILURE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#isFailure--\
\[IF_SUCCESS_OR_ELSE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#ifSuccessOrElse-java.util.function.Consumer,java.util.function.Consumer-\
\[IF_SUCCESS]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#ifSuccess-java.util.function.Consumer-\
\[IF_FAILURE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#ifFailure-java.util.function.Consumer-\
\[OR_ELSE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#orElse-S-\
\[OR_ELSE_MAP]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#orElseMap-java.util.function.Function-\
\[OPTIONAL]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#optional--\
\[OPTIONAL_FAILURE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#optionalFailure--\
\[FILTER]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#filter-java.util.function.Predicate,java.util.function.Function-\
\[MAP]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#map-java.util.function.Function,java.util.function.Function-\
\[MAP_SUCCESS]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#mapSuccess-java.util.function.Function-\
\[MAP_FAILURE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#mapFailure-java.util.function.Function-\
\[FLATMAP]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#flatMap-java.util.function.Function,java.util.function.Function-\
\[FLATMAP_SUCCESS]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#flatMapSuccess-java.util.function.Function-\
\[FLATMAP_FAILURE]: [https://dev.leakyabstractions.com/result/javadoc/{{](https://dev.leakyabstractions.com/result/javadoc/%7B%7B) site.current_version }}/com/leakyabstractions/result/Result.html#flatMapFailure-java.util.function.Function-
