pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("androidProjectConfig") {
            from(files("gradle/android-project-config.versions.toml"))
        }
        create("gradleProjectConfig") {
            from(files("gradle/gradle-project-config.versions.toml"))
        }
    }
}

rootProject.name = "Wallet-Leaks"
includeBuild("build-logic")
include(":app")
include(":core:domain", ":core:data", ":core:presentation")
include(":konsist")
include(
    ":feature:authentication:data",
    "feature:authentication:domain",
    ":feature:authentication:presentation"
)
include(
    ":feature:main:data",
    ":feature:main:domain",
    ":feature:main:presentation"
)