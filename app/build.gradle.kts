@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = libs.versions.packageName.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.packageName.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = libs.versions.androidTestInstrumentation.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    packagingOptions {
        resources.merges.add("META-INF/gradle/incremental.annotation.processors")
        resources.merges.add("META-INF/LICENSE.md")
        resources.merges.add("META-INF/LICENSE-notice.md")
    }
}

dependencies {
    //dependent modules
    implementation(project(":presentation:home"))
    implementation(project(":core:di"))
    implementation(project(":core:common"))

    //dagger
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    //ui
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.androidx.navigation)
    //other
    implementation(libs.logging.timber)

    implementation(libs.bundles.testing.common)
    implementation(libs.bundles.testing.common.android)
}

kapt {
    correctErrorTypes = true
}
