import java.util.Properties
plugins {
    libs.plugins.apply {
        id(agp.application.get().pluginId)
        id(kotlin.android.get().pluginId)
        id(google.services.get().pluginId)
        id(triplet.playpublisher.get().pluginId) version (libs.versions.triplet.playpublisher.get())
    }
}

android {
    androidProjectConfig.versions.apply {
        namespace = namespaces.android.app.get()
        compileSdk = sdk.compile.get().toInt()
        defaultConfig {
            applicationId =
                namespaces.android.app.get().replace(".android", "")
            minSdk = sdk.min.get().toInt()
            targetSdk = sdk.target.get().toInt()
            val version =
                "${versioning.major.get()}.${versioning.feature.get()}.${versioning.patch.get()}"
            versionCode =
                version.replace(".", "").toInt()
            versionName =
                version
        }
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.compose.get()
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        buildTypes {
            release {
                isDebuggable = false
                isShrinkResources = true
                isMinifyEnabled = true
            }
            debug {
                isDebuggable = true
                isShrinkResources = false
                isMinifyEnabled = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = gradleProjectConfig.versions.kotlin.options.jvm.get()
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

tasks.register("updateBuildCount") {
    doLast {
        val readmeFile = File(project.rootDir, "README.md")
        var buildCount = 0

        val readmeContents = readmeFile.readText()
        val buildCountPattern = """Total build count: (\d+)""".toRegex()
        val matchResult = buildCountPattern.find(readmeContents)

        matchResult?.let {
            buildCount = matchResult.groupValues[1].toIntOrNull() ?: 0
        }

        buildCount++
        readmeFile.writeText(
            readmeContents.replaceFirst(
                buildCountPattern,
                "Total build count: $buildCount"
            )
        )
    }
}

pluginManager.withPlugin("com.android.application") {
    tasks.whenTaskAdded {
        if (name.startsWith("assemble")) {
            finalizedBy("updateBuildCount")
        }
    }
}