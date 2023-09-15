plugins {
    alias(libs.plugins.kotlin.jvm)
    id(libs.plugins.walletleaks.layer.domain.get().pluginId)
}

//java {
//    sourceCompatibility = JavaVersion.VERSION_19
//    targetCompatibility = JavaVersion.VERSION_19
//}