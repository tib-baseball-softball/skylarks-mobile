# DefaultAPI

All URIs are relative to *https://config.app.berlinskylarks.de/api/v1*

| Method | HTTP request | Description |
| ------------- | ------------- | ------------- |
| [**getConfigs**](DefaultAPI.md#getConfigs) | **GET** /configs | List all configurations |
| [**getFlags**](DefaultAPI.md#getFlags) | **GET** /flags | List all feature flags |


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

val apiInstance = DefaultAPI()
val context : ApplicationContext =  // ApplicationContext | Optional application context to filter for.
try {
    val result : kotlin.collections.List<ConfigurationDTO> = apiInstance.getConfigs(context)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultAPI#getConfigs")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultAPI#getConfigs")
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

val apiInstance = DefaultAPI()
val excludedConfigID : kotlin.String = 123e4567-e89b-12d3-a456-426655440000 // kotlin.String | Optional UUID to filter out flags already linked to this configuration.
try {
    val result : kotlin.collections.List<FeatureFlagDTO> = apiInstance.getFlags(excludedConfigID)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling DefaultAPI#getFlags")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling DefaultAPI#getFlags")
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

