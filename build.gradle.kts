import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.agp.application) apply false
    alias(libs.plugins.agp.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.google.devtools.ksp) apply false
}

tasks.register(gradleProjectConfig.versions.tasks.clean.get(), Delete::class) {
    delete(rootProject.buildDir)
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions { jvmTarget = gradleProjectConfig.versions.kotlin.options.jvm.get() }
}