import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    kotlin("plugin.serialization") version "1.9.22"
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("de.jensklingenberg.ktorfit") version "2.5.1"
}

kotlin {
    androidTarget()
    jvm("desktop")
    wasmJs {
        browser {
            testTask {
                useMocha()
            }
        }
        binaries.executable() // ← necesario para generar ejecutable web
    }
    jvmToolchain(11)

    val ktorfitVersion = "2.5.1"
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("io.ktor:ktor-client-core:2.3.11")
            implementation("io.ktor:ktor-client-okhttp:2.3.11")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.11")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
            implementation("io.coil-kt:coil-compose:2.5.0")
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfitVersion")
            implementation("io.ktor:ktor-client-core:3.1.2")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.2")
            implementation("io.ktor:ktor-client-content-negotiation:3.1.2")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("io.ktor:ktor-client-core:2.3.11")
            implementation("io.ktor:ktor-client-okhttp:2.3.11")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.11")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
            implementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.9.4")
        }
        wasmJsMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
            implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfitVersion")
        }

    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "org.example.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}
