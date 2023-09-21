package org.tbm.walletleaks.buildlogic.convention.plugins.base

import com.android.build.api.dsl.LibraryBuildFeatures
import com.android.build.api.dsl.LibraryBuildType
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.tbm.walletleaks.buildlogic.convention.extensions.androidProjectConfig
import org.tbm.walletleaks.buildlogic.convention.extensions.extractPluginId
import org.tbm.walletleaks.buildlogic.convention.extensions.extractPrimitive
import org.tbm.walletleaks.buildlogic.convention.extensions.gradleProjectConfig
import org.tbm.walletleaks.buildlogic.convention.extensions.libs


internal abstract class AndroidLibraryPlugin(
    private val projectConfiguration: Project.() -> Unit = {},
    private val androidLibraryConfiguration: LibraryExtension.() -> Unit = {},
    private val releaseLibraryBuildType: LibraryBuildType.() -> Unit = {},
    private val debugLibraryBuildType: LibraryBuildType.() -> Unit = releaseLibraryBuildType,
    private val buildFeaturesConfiguration: LibraryBuildFeatures.() -> Unit = {},
) : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply {
                apply(libs.plugins.agp.library.extractPluginId())
                apply(libs.plugins.kotlin.android.extractPluginId())
            }

            extensions.configure<LibraryExtension> {
                namespace =
                    "${androidProjectConfig.versions.namespaces.common.get()}.${
                        path.replace(":feature", "").split(":").filter { it.isNotBlank() }
                            .joinToString(".")
                    }"

                compileSdk = androidProjectConfig.versions.sdk.compile.extractPrimitive()

                defaultConfig {
                    minSdk = androidProjectConfig.versions.sdk.min.extractPrimitive()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                        releaseLibraryBuildType()
                    }

                    debug {
                        isMinifyEnabled = false
                        debugLibraryBuildType()
                    }
                }

                compileOptions {
                    sourceCompatibility =
                        JavaVersion.values()[gradleProjectConfig.versions.kotlin.options.jvm.extractPrimitive<Int>() - 1]
                    targetCompatibility =
                        JavaVersion.values()[gradleProjectConfig.versions.kotlin.options.jvm.extractPrimitive<Int>() - 1]
                }

                tasks.withType<KotlinCompile>().configureEach {
                    kotlinOptions {
                        jvmTarget = gradleProjectConfig.versions.kotlin.options.jvm.get()
                    }
                }

                buildFeatures {
                    buildFeaturesConfiguration()
                }

                if (buildFeatures.compose == true)
                    composeOptions {
                        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.compose.get()
                    }

                androidLibraryConfiguration()
            }

            projectConfiguration()
        }
    }
}