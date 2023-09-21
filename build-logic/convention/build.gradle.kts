import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = gradleProjectConfig.versions.kotlin.options.jvm.get()
    }
}
sourceSets {
    main {
        kotlin {
            srcDir("../.gradle/7.5.1/dependencies-accessors")
        }
    }
}

dependencies {
    compileOnly(libs.agp.tools.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.google.devtools.ksp)
}

gradlePlugin {
    plugins {

        with(libs.plugins.walletleaks) {
            register(android.plain.get().pluginId) {
                id = android.plain.get().pluginId
                implementationClass =
                    gradlePluginsConfig.versions.android.plain.implementation.get()
            }

            register(layer.data.get().pluginId) {
                id = layer.data.get().pluginId
                implementationClass =
                    gradlePluginsConfig.versions.layer.data.implementation.get()
            }

            register(layer.domain.get().pluginId) {
                id = layer.domain.get().pluginId
                implementationClass =
                    gradlePluginsConfig.versions.layer.domain.implementation.get()
            }

            register(layer.presentation.get().pluginId) {
                id = layer.presentation.get().pluginId
                implementationClass =
                    gradlePluginsConfig.versions.layer.presentation.implementation.get()
            }
        }
    }
}