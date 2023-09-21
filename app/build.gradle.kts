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
        namespace = namespaces.common.get()
        compileSdk = sdk.compile.get().toInt()
        defaultConfig {
            applicationId =
                namespaces.common.get()
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
    implementation(projects.feature.authentication.presentation)
    implementation(projects.feature.main.presentation)
    implementation(libs.bundles.androidx.compose)
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