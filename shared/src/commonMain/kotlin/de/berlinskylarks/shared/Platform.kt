package de.berlinskylarks.shared

expect fun platform(): String

class Greeting {
    fun greet(): String {
        return "Hello, ${platform()}!"
    }
}