# Transforming Values

In the previous section, we looked at how to reject or accept a success value based on a filter.

We can also transform success/failure values held by _Result_ objects with the `map...` family of methods:

```java
@Test
void should_return_string_length() {
    // Given
    final Result<String, Integer> result = success("ABCD");
    // When
    final Result<Integer, String> mapped = result.mapSuccess(String::length);
    // Then
    assertThat(mapped.orElseThrow()).isEqualTo(4);
}
```

In this example, we wrap a string inside a _Result_ object and use its \[`mapSuccess()`\]\[MAP_SUCCESS\] method to manipulate it \(here we calculate its length\). Note that we can specify the action as a method reference, or a lambda. In any case, the result of this action gets wrapped inside a new \_Result_ object. And then we call the appropriate method on the returned result to retrieve its value.

There is another \[`map()`\]\[MAP\] method to transform either success/failure value at once:

```java
@Test
void should_return_upper_case() {
    // Given
    final Result<String, String> result = success("Hello World!");
    // When
    final Result<String, String> mapped = result
        .map(String::toUpperCase, String::toLowerCase);
    // Then
    assertThat(mapped.orElseThrow()).isEqualTo("HELLO WORLD!");
}

@Test
void should_return_lower_case() {
    // Given
    final Result<String, String> result = failure("Hello World!");
    // When
    final Result<String, String> mapped = result
        .map(String::toUpperCase, String::toLowerCase);
    // Then
    assertThat(mapped.getFailureOrElseThrow()).isEqualTo("hello world!");
}
```

And the \[`mapFailure()`\]\[MAP\_FAILURE\] method allows us to transform failure values only:

```java
@Test
void should_return_is_empty() {
    // Given
    final Result<Integer, String> result = failure("");
    // When
    final Result<Integer, Boolean> mapped = result.mapFailure(String::isEmpty);
    // Then
    assertThat(mapped.getFailureOrElseThrow()).isTrue();
}
```

Just like the `map...` methods, we also have the `flatMap...` family of methods as an alternative for transforming values. The difference is that `map...` methods don't alter the success/failure state of the result, whereas with `flatMap...` ones, you can start with a successful result and end up with a failed one, and _vice versa_.

Previously, we created simple `String` and `Integer` objects for wrapping in a _Result_ instance. However, frequently, we will receive these objects as we invoke third-party methods.

To get a clearer picture of the difference, let's have a look at a `User` object that takes a _name_ and a boolean flag that determines if the user has custom configuration. It also has a method `getCustomConfigPath` which returns a _Result_ containing either the path to the user configuration file, or a `Problem` object describing why the path cannot be obtained:

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

Now suppose we have a method `openFile` which checks if a given file exists and returns a result containing the file object or a `Problem` object explaining why the file cannot be retrieved:

```java
Result<File, Problem> openFile(String path) {
    final File file = new File(path);
    return file.exists() ? success(file) : failure(new FileProblem("File does not exist"));
}
```

If we wanted to obtain the file path from the user _and then_ invoke the above method to get the file object, we could use \[`flatMapSuccess`\]\[FLATMAP\_SUCCESS\] to fluently transform one result into another:

```java
@Test
void should_contain_file() {
    // Given
    final User user = new User("Rachel", true);
    // When
    final Result<File, Problem> result = user.getCustomConfigPath()
        .flatMapSuccess(this::openFile);
    // Then
    assertThat(result.orElseThrow()).isAbsolute();
}

@Test
void should_contain_user_problem() {
    // Given
    final User user = new User("Monica", false);
    // When
    final Result<File, Problem> result = user.getCustomConfigPath()
        .flatMapSuccess(this::openFile);
    // Then
    assertThat(result.getFailureOrElseThrow()).isInstanceOf(UserProblem.class);
}

@Test
void should_contain_file_problem() {
    // Given
    final User user = new User("../../wrong//path/", true);
    // When
    final Result<File, Problem> result = user.getCustomConfigPath()
        .flatMapSuccess(this::openFile);
    // Then
    assertThat(result.getFailureOrElseThrow()).isInstanceOf(FileProblem.class);
}
```

There is another \[`flatMap`\]\[FLATMAP\] method to transform either success/failure values at once:

```java
@Test
void should_contain_123() {
    // Given
    final User user = new User("Phoebe", false);
    // When
    final Result<File, Integer> result = user.getCustomConfigPath()
        .flatMap(this::openFile, f -> 123);
    // Then
    assertThat(result.getFailureOrElseThrow()).isEqualTo(123);
}
```

And the \[`flatMapFailure()`\]\[FLATMAP\_FAILURE\] method allows us to transform failure values only:

```java
@Test
void should_contain_error() {
    // Given
    final User user = new User("Joey", false);
    // When
    final Result<String, String> result = user.getCustomConfigPath()
        .flatMapFailure(f -> "error");
    // Then
    assertThat(result.getFailureOrElseThrow()).isEqualTo("error");
}
```

