package org.tbm.walletleaks.buildlogic.convention.plugins.layers

import org.gradle.kotlin.dsl.dependencies
import org.tbm.walletleaks.buildlogic.convention.extensions.implementation
import org.tbm.walletleaks.buildlogic.convention.extensions.libs
import org.tbm.walletleaks.buildlogic.convention.plugins.base.KotlinLibraryPlugin

internal class DomainPlugin : KotlinLibraryPlugin({
    dependencies {
        if (path.split(":").first { it.isNotBlank() } != "core")
            implementation(project(":core:domain"))
        implementation(libs.bundles.kotlinx.core)
        implementation(libs.androidx.paging.common)
        implementation(libs.javax.inject)
    }
})