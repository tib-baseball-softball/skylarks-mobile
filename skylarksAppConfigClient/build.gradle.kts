plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinxSerialization)
}

group = "de.berlinskylarks"
version = "1.0.0"

kotlin {
    jvm()

    iosArm64()
    macosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.serialization.core)

                api(libs.ktor.client.core)
                api(libs.ktor.client.serialization)
                api(libs.ktor.client.content.negotiation)
                api(libs.ktor.serialization.kotlinx.json)

                api(libs.kotlinx.datetime)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.ktor.client.mock)
            }
        }

        all {
            languageSettings {
                optIn("kotlin.time.ExperimentalTime")
            }
        }
    }
}

tasks {
    register("test") {
        dependsOn("allTests")
    }
}
