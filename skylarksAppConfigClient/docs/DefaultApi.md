# DefaultApi

All URIs are relative to *https://config.app.berlinskylarks.de/api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getConfigs**](DefaultApi.md#getConfigs) | **GET** /configs | List all configurations |
| [**getFlags**](DefaultApi.md#getFlags) | **GET** /flags | List all feature flags |
| [**healthCheck**](DefaultApi.md#healthCheck) | **GET** /health | Health Check |


<a id="getConfigs"></a>
# **getConfigs**
> kotlin.collections.List&lt;ConfigurationDTO&gt; getConfigs(context)

List all configurations

Provides a list of all configurations with their associated feature flags.

### Example
```kotlin
// Import classes:
//import de.berlinskylarks.appconfigclient.infrastructure.*
//import de.berlinskylarks.appconfigclient.models.*

val apiInstance = DefaultApi()
val context : ApplicationContext =  // ApplicationContext | Optional application context to filter for.
try {
    val result : kotlin.collections.List<ConfigurationDTO> = apiInstance.getConfigs(context)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#getConfigs")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#getConfigs")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **context** | [**ApplicationContext**](.md)| Optional application context to filter for. | [optional] [enum: production, staging, development] |

### Return type

[**kotlin.collections.List&lt;ConfigurationDTO&gt;**](ConfigurationDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="getFlags"></a>
# **getFlags**
> kotlin.collections.List&lt;FeatureFlagDTO&gt; getFlags(excludedConfigID)

List all feature flags

Provides an API list of feature flags, optionally filtering out those already linked to a configuration.

### Example
```kotlin
// Import classes:
//import de.berlinskylarks.appconfigclient.infrastructure.*
//import de.berlinskylarks.appconfigclient.models.*

val apiInstance = DefaultApi()
val excludedConfigID : kotlin.String = 123e4567-e89b-12d3-a456-426655440000 // kotlin.String | Optional UUID to filter out flags already linked to this configuration.
try {
    val result : kotlin.collections.List<FeatureFlagDTO> = apiInstance.getFlags(excludedConfigID)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#getFlags")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#getFlags")
    e.printStackTrace()
}
```

### Parameters
| Name | Type | Description  | Notes |
| ------------- | ------------- | ------------- | ------------- |
| **excludedConfigID** | **kotlin.String**| Optional UUID to filter out flags already linked to this configuration. | [optional] |

### Return type

[**kotlin.collections.List&lt;FeatureFlagDTO&gt;**](FeatureFlagDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a id="healthCheck"></a>
# **healthCheck**
> HealthCheck healthCheck()

Health Check

Returns a simple health check message.

### Example
```kotlin
// Import classes:
//import de.berlinskylarks.appconfigclient.infrastructure.*
//import de.berlinskylarks.appconfigclient.models.*

val apiInstance = DefaultApi()
try {
    val result : HealthCheck = apiInstance.healthCheck()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultApi#healthCheck")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultApi#healthCheck")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**HealthCheck**](HealthCheck.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

