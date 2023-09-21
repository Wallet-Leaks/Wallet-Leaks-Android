package org.tbm.walletleaks.buildlogic.convention.plugins.layers

import org.gradle.kotlin.dsl.dependencies
import org.tbm.walletleaks.buildlogic.convention.extensions.extractPluginId
import org.tbm.walletleaks.buildlogic.convention.extensions.implementation
import org.tbm.walletleaks.buildlogic.convention.extensions.ksp
import org.tbm.walletleaks.buildlogic.convention.extensions.libs
import org.tbm.walletleaks.buildlogic.convention.plugins.base.AndroidLibraryPlugin

internal class DataPlugin : AndroidLibraryPlugin(projectConfiguration = {

    pluginManager.apply {
        apply(libs.plugins.kotlin.serialization.extractPluginId())
        apply(libs.plugins.google.devtools.ksp.extractPluginId())
    }

    dependencies {
        implementation(project(":core:data"))
        implementation(project(path.replace(":data", ":domain")))
        implementation(libs.bundles.kotlinx.core)
        implementation(libs.bundles.ktor.client)
        implementation(libs.google.dagger)
        ksp(libs.google.dagger.compiler)
    }
}, releaseLibraryBuildType = {
    buildConfigField("String", "DEV_BASE_URL", "\"http://134.122.75.14:8999\"")
}, buildFeaturesConfiguration = {
    buildConfig = true
}
)