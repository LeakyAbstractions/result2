---
description: Adding Result as a dependency to your build
---

# Adding Result to Your Build

The library requires JDK 1.8 or higher. Other than that, it has no external dependencies and it is very lightweight.\
Adding it to your build should be very easy.

Artifact coordinates:

* Group ID: `com.leakyabstractions`
* Artifact ID: `result`
* Version: `1.0.0.0`

### Maven

To add a dependency on Result using [**Maven**](https://maven.apache.org), use the following:

```xml
<dependencies>
    <dependency>
        <groupId>com.leakyabstractions</groupId>
        <artifactId>result</artifactId>
        <version>{{ site.current_version }}</version>
        <type>pom</type>
    </dependency>
</dependencies>
```

### Gradle

To add the dependency using [**Gradle**](https://gradle.org), if you are building an application that will use

Result internally:

```
dependencies {
    implementation 'com.leakyabstractions:result:{{ site.current_version }}'
}
```

If you are building a library that will use Result type in its public API, you should use instead:

```
dependencies {
    api 'com.leakyabstractions:result:{{ site.current_version }}'
}
```

For more information on when to use api and implementation, read the [Gradle documentation on API and implementation separation](https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_separation).
