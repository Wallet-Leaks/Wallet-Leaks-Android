dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {

        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }

        create("androidProjectConfig") {
            from(files("../gradle/android-project-config.versions.toml"))
        }

        create("gradleProjectConfig") {
            from(files("../gradle/gradle-project-config.versions.toml"))
        }

        create("gradlePluginsConfig") {
            from(files("gradle/gradle-plugins-config.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")