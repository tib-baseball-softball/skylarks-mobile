package de.berlinskylarks.shared.errors

import de.berlinskylarks.appconfigclient.models.ApplicationContext
import io.sentry.kotlin.multiplatform.Sentry

fun initializeSentry() {
    // TODO: verify this
    val context = ApplicationContext.decode(System.getenv("APP_CONTEXT"))
    val isDev = context != ApplicationContext.production

    Sentry.init { options ->
        options.dsn = System.getenv("SENTRY_DSN")
        options.sendDefaultPii = false
        options.environment = context?.name ?: "development"
        options.debug = isDev
    }
}