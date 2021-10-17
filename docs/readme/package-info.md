---
description: A Java library to handle success and failure without exceptions
---

# com.leakyabstractions.result

### Result Library for Java

The purpose of this library is to provide a type-safe encapsulation of operation results that may have succeeded or failed, instead of throwing exceptions.

If you like [`Optional`](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html?is-external=true) but feel that it sometimes falls too short, you'll love `Result`.

The best way to think of `Result` is as a super-powered version of `Optional`. The only difference is that whereas `Optional` may contain a successful value or express the absence of a value, `Result` contains either a successful value or a failure value that explains what went wrong.

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

#### Result Library in a Nutshell

Before `Result`, we would wrap exception-throwing `foobar` method invocation inside a `try` block so that errors can be handled inside a `catch` block.

```
 public int getFoobarLength() {
     int length;
     try {
         final String result = foobar();
         this.commit(result);
         length = result.length();
     } catch (SomeException problem) {
         this.rollback(problem);
         length = -1;
     }
     return length;
 }
 
 
```

This approach is lengthy, and that's not the only problem -- it's also slow. Conventional wisdom says that exceptional logic shouldn't be used for normal program flow. `Result` makes us deal with expected, non-exceptional error situations explicitly as a way of enforcing good programming practices.

Let's now look at how the above code could be refactored if method `foobar` returned a `Result` object instead of throwing an exception:

```
 public int getFoobarLength() {
     final Result<String, SomeFailure> result = foobar();
     result.ifSuccessOrElse(this::commit, this::rollback);
     final Result<Integer, SomeFailure> resultLength = result.mapSuccess(String::length);
     return resultLength.orElse(-1);
 }
 
 
```

In the above example, we use only four lines of code to replace the ten that worked in the first example. But we can make it even shorter by chaining methods in typical functional programming style:

```
 public int getFoobarLength() {
     return foobar().ifSuccessOrElse(this::commit, this::rollback).mapSuccess(String::length).orElse(-1);
 }
 
 
```

In fact, since we are using `-1` here just to signal that the underlying operation failed, we'd be better off returning a `Result` object upstream:

```
 public Result<Integer, SomeFailure> getFoobarLength() {
     return foobar().ifSuccessOrElse(this::commit, this::rollback).mapSuccess(String::length);
 }
 
 
```

This allows others to easily compose operations on top of ours, just like we did with foobar.
