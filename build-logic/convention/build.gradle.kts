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

dependencies {
    compileOnly(libs.agp.tools.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.google.devtools.ksp)
}

gradlePlugin {
    plugins {

        register(libs.plugins.walletleaks.layer.data.get().pluginId) {
            id = libs.plugins.walletleaks.layer.data.get().pluginId
            implementationClass = gradlePluginsConfig.versions.layer.data.implementation.get()
        }

        register(libs.plugins.walletleaks.layer.domain.get().pluginId) {
            id = libs.plugins.walletleaks.layer.domain.get().pluginId
            implementationClass = gradlePluginsConfig.versions.layer.domain.implementation.get()
        }

        register(libs.plugins.walletleaks.layer.presentation.get().pluginId) {
            id = libs.plugins.walletleaks.layer.presentation.get().pluginId
            implementationClass =
                gradlePluginsConfig.versions.layer.presentation.implementation.get()
        }
    }
}