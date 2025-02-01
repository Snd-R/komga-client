import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.mavenPublish)
    signing
}

group = "io.github.snd-r"
version = "0.8.0"

kotlin {
    jvmToolchain(17)
    androidTarget {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
        publishLibraryVariants("release")
    }
    jvm {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_17) }
    }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "komga-client"
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.logging)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.sse)
        }

        jvmMain.dependencies {
            implementation(libs.okhttp)
            implementation(libs.okhttp.sse)
        }

        androidMain.dependencies {
            implementation(libs.okhttp)
            implementation(libs.okhttp.sse)
        }
        wasmJsMain.dependencies {
            implementation(libs.kotlinx.browser)
        }
    }

}
android {
    namespace = "snd.komga.client"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

signing {
    useGpgCmd()
}
