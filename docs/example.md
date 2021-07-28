---
title: My example
description: My description
---

# Example

Example

image 1 start

![](.gitbook/assets/result-banner-centered%20%281%29.png)

image 1 end

> ## page title:
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

```text
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

{% api-method method="get" host="url" path="" %}
{% api-method-summary %}
method
{% endapi-method-summary %}

{% api-method-description %}
Method description
{% endapi-method-description %}

{% api-method-spec %}
{% api-method-request %}
{% api-method-path-parameters %}
{% api-method-parameter name="param2" type="object" required=false %}
description
{% endapi-method-parameter %}

{% api-method-parameter name="param1" type="string" required=true %}
description
{% endapi-method-parameter %}
{% endapi-method-path-parameters %}

{% api-method-headers %}
{% api-method-parameter name="header1" type="string" required=false %}
description
{% endapi-method-parameter %}
{% endapi-method-headers %}

{% api-method-query-parameters %}
{% api-method-parameter name="param1" type="array" required=false %}
description
{% endapi-method-parameter %}
{% endapi-method-query-parameters %}

{% api-method-form-data-parameters %}
{% api-method-parameter name="param1" type="number" required=true %}
description
{% endapi-method-parameter %}
{% endapi-method-form-data-parameters %}

{% api-method-body-parameters %}
{% api-method-parameter name="param1" type="boolean" required=false %}
description
{% endapi-method-parameter %}
{% endapi-method-body-parameters %}
{% endapi-method-request %}

{% api-method-response %}
{% api-method-response-example httpCode=200 %}
{% api-method-response-example-description %}
Response description
{% endapi-method-response-example-description %}

```text
response 1
```
{% endapi-method-response-example %}

{% api-method-response-example httpCode=302 %}
{% api-method-response-example-description %}
Response description
{% endapi-method-response-example-description %}

```text
response 2
```
{% endapi-method-response-example %}
{% endapi-method-response %}
{% endapi-method-spec %}
{% endapi-method %}

