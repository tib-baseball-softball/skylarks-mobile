package de.berlinskylarks.shared.data.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

object APIClient {
    var client = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 45000L
            connectTimeoutMillis = 45000L
            socketTimeoutMillis = 45000L
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
    }
}
