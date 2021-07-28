---
description: Adding result to your build using Gradle
---

# Gradle

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

{% hint style="info" %}
For more information on when to use `api` and `implementation`, read the [Gradle documentation on API and implementation separation](https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_separation).
{% endhint %}

