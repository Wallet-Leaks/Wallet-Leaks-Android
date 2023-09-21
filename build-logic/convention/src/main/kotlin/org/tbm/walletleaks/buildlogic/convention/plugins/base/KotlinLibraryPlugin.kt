package org.tbm.walletleaks.buildlogic.convention.plugins.base

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.tbm.walletleaks.buildlogic.convention.extensions.extractPluginId
import org.tbm.walletleaks.buildlogic.convention.extensions.extractPrimitive
import org.tbm.walletleaks.buildlogic.convention.extensions.gradleProjectConfig
import org.tbm.walletleaks.buildlogic.convention.extensions.libs

internal abstract class KotlinLibraryPlugin(private val configuration: Project.() -> Unit = {}) :
    Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply(libs.plugins.java.library.extractPluginId())
                apply(libs.plugins.kotlin.jvm.extractPluginId())
            }

            extensions.configure<JavaPluginExtension> {
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

            configuration()
        }
    }
}