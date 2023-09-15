plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    id(libs.plugins.walletleaks.layer.domain.get().pluginId)
}