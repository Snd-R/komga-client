rootProject.name = "komga-client"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        val kotlinVersion = "2.0.0"
        val agpVersion = "8.2.0"
        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("plugin.serialization").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)
        id("com.android.library").version(agpVersion)
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}
