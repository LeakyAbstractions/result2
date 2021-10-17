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



### Asserting Result objects <a href="creating-result-objects" id="creating-result-objects"></a>

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
