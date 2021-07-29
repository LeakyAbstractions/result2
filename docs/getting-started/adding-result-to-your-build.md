---
description: Adding Result as a dependency to your build
---

# Adding Result to Your Build

The library requires JDK 1.8 or higher. Other than that, it has no external dependencies and it is very lightweight. Adding it to your build should be very easy.

Artifacts are available in [Maven Central](https://search.maven.org/artifact/com.leakyabstractions/result):

* Group ID: `com.leakyabstractions`
* Artifact ID: `result`

## Maven

To add a dependency on _Result_ using [**Maven**](https://maven.apache.org/), use the following:

```markup
<dependencies>
    <dependency>
        <groupId>com.leakyabstractions</groupId>
        <artifactId>result</artifactId>
        <version>1.0.0.0</version>
        <type>pom</type>
    </dependency>
</dependencies>
```

## Gradle

To add the dependency using [**Gradle**](https://gradle.org/), if you are building an application that will use _Result_ internally:

```text
dependencies {
    implementation 'com.leakyabstractions:result:1.0.0.0'
}
```

If you are building a library that will use _Result_ type in its public API, you should use instead:

```text
dependencies {
    api 'com.leakyabstractions:result:1.0.0.0'
}
```

{% hint style="info" %}
For more information on when to use `api` and `implementation`, read the [Gradle documentation on API and implementation separation](https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_separation).
{% endhint %}

