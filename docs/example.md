---
description: My description
---

# Example

Example

image 1 start

![](<.gitbook/assets/result-banner-centered (1) (1).png>)

image 1 end

> ### page title:
>
> page description:

image 2 start

![](https://github.com/LeakyAbstractions/result/tree/b0550d8da3bd207bd1b0bd3a0426b013d3356c7a/docs/docs/result-banner-centered.png)

image 2 end

* red
* green
* blue
* one
* two
* three

{% hint style="info" %}
info
{% endhint %}

{% hint style="warning" %}
warning
{% endhint %}

{% hint style="danger" %}
danger
{% endhint %}

{% hint style="success" %}
success
{% endhint %}

* [ ] Task1
* [ ] Task 2
* [x] Task 3
* [ ] Task 4

```
code block
```

{% tabs %}
{% tab title="First Tab" %}
foo
{% endtab %}

{% tab title="Second Tab" %}
bar
{% endtab %}

{% tab title="Third Tab" %}
foobar
{% endtab %}
{% endtabs %}

{% swagger baseUrl="url" method="get" summary="method" %}
{% swagger-description %}
Method description
{% endswagger-description %}

{% swagger-parameter name="param2" type="object" in="path" %}
description
{% endswagger-parameter %}

{% swagger-parameter name="param1" type="string" in="path" %}
description
{% endswagger-parameter %}

{% swagger-parameter name="header1" type="string" in="header" %}
description
{% endswagger-parameter %}

{% swagger-parameter name="param1" type="array" in="query" %}
description
{% endswagger-parameter %}

{% swagger-parameter name="param1" type="number" in="body" %}
description
{% endswagger-parameter %}

{% swagger-parameter name="param1" type="boolean" in="body" %}
description
{% endswagger-parameter %}

{% swagger-response status="200" description="" %}
```
response 1
```
{% endswagger-response %}

{% swagger-response status="302" description="" %}
```
response 2
```
{% endswagger-response %}
{% endswagger %}
