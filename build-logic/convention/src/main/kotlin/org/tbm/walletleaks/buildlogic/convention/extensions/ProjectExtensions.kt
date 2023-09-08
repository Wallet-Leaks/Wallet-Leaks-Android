package org.tbm.walletleaks.buildlogic.convention.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

internal inline val Project.libs
    inline get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal inline val Project.androidProjectConfig
    inline get() = extensions.getByType<VersionCatalogsExtension>()
        .named("androidProjectConfig")

internal inline val Project.gradleProjectConfig
    inline get() = extensions.getByType<VersionCatalogsExtension>()
        .named("gradleProjectConfig")

internal fun DependencyHandlerScope.implementation(dependencyNotation: Any) {
    "implementation"(dependencyNotation)
}

internal fun DependencyHandlerScope.testImplementation(dependencyNotation: Any) {
    "testImplementation"(dependencyNotation)
}

internal fun DependencyHandlerScope.androidTestImplementation(dependencyNotation: Any) {
    "androidTestImplementation"(dependencyNotation)
}

internal fun DependencyHandlerScope.debugImplementation(dependencyNotation: Any) {
    "debugImplementation"(dependencyNotation)
}

internal fun DependencyHandlerScope.api(dependencyNotation: Any) {
    "api"(dependencyNotation)
}

internal fun DependencyHandlerScope.ksp(dependencyNotation: Any) {
    "ksp"(dependencyNotation)
}

internal fun DependencyHandlerScope.coreLibraryDesugaring(dependencyNotation: Any) {
    "coreLibraryDesugaring"(dependencyNotation)
}
