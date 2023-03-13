@file:Suppress("UnstableApiUsage")
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.parcelize)
    kotlin("kapt")
}

android {
    namespace = libs.versions.packageName.get() + ".presentation.home"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //dependent projects
    implementation(project(":core:common"))
    implementation(project(":core:common-android"))
    implementation(project(":domain:sport_events:model"))
    implementation(project(":domain:sport_events:usecases"))
    implementation(project(":data:sport_events:repo"))

    //other
    implementation(libs.logging.timber)

    //dagger
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    //ui
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidx.ui.common)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.recyclerview)
}