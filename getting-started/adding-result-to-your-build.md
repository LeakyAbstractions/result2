# Adding Result to Your Build

The library requires JDK 1.8 or higher. Other than that, it has no external dependencies and it is very lightweight. Adding it to your build should be very easy.

Artifact coordinates:

* Group ID: `com.leakyabstractions`
* Artifact ID: `result`
* Version: `{{ site.current_version }}`

To add a dependency on _Result_ using [**Maven**](https://maven.apache.org/), use the following:

```markup
<dependencies>
    <dependency>
        <groupId>com.leakyabstractions</groupId>
        <artifactId>result</artifactId>
        <version>{{ site.current_version }}</version>
        <type>pom</type>
    </dependency>
</dependencies>
```

To add the dependency using [**Gradle**](https://gradle.org/), if you are building an application that will use _Result_ internally:

```text
dependencies {
    implementation 'com.leakyabstractions:result:{{ site.current_version }}'
}
```

If you are building a library that will use _Result_ type in its public API, you should use instead:

```text
dependencies {
    api 'com.leakyabstractions:result:{{ site.current_version }}'
}
```

For more information on when to use _api_ and _implementation_, read the [Gradle documentation on API and implementation separation](https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_separation).

