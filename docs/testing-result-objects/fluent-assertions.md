---
description: Asserting Result Objects
---

# Fluent Assertions

You can use fluent assertions (based on [AssertJ](https://assertj.github.io)) for Result objects in your unit tests.

To add a dependency on Result assertions using **Maven**, use the following:

```xml
<dependency>
    <groupId>com.leakyabstractions</groupId>
    <artifactId>result-assertj</artifactId>
    <version>1.0.0.0</version>
    <scope>test</scope>
</dependency>
```

To add a dependency using **Gradle**:

```
dependencies {
    testImplementation 'com.leakyabstractions:result-assertj:{{ site.current_version }}'
}
```

This allows you to use Result assertions in your tests via `assertThat`:

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

If, for some reason, you cannot statically import static method `ResultAssertions.assertThat `you can use static method `ResultAssert.assertThatResult` instead:

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
