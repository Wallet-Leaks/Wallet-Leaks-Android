plugins {
    id(libs.plugins.walletleaks.android.plain.get().pluginId)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.lemonappdev.konsist)
    implementation(libs.junit)
    testImplementation(libs.lemonappdev.konsist)
    testImplementation(libs.junit)
}