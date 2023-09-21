package org.tbm.walletleaks.buildlogic.convention.plugins.layers

import org.gradle.kotlin.dsl.dependencies
import org.tbm.walletleaks.buildlogic.convention.extensions.extractPluginId
import org.tbm.walletleaks.buildlogic.convention.extensions.implementation
import org.tbm.walletleaks.buildlogic.convention.extensions.ksp
import org.tbm.walletleaks.buildlogic.convention.extensions.libs
import org.tbm.walletleaks.buildlogic.convention.plugins.base.AndroidLibraryPlugin

internal class PresentationPlugin : AndroidLibraryPlugin({

    pluginManager.apply {
        apply(libs.plugins.kotlin.serialization.extractPluginId())
        apply(libs.plugins.google.devtools.ksp.extractPluginId())
    }

    dependencies {
        implementation(project(":core:presentation"))
        implementation(project(path.replace(":presentation", ":data")))
        implementation(libs.bundles.androidx.compose)
        implementation(libs.bundles.kotlinx.android)
        implementation(libs.google.dagger)
        ksp(libs.google.dagger.compiler)
    }
}, buildFeaturesConfiguration = {
    compose = true
})