@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
  //  alias(libs.plugins.dagger.hilt)
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
        resources.excludes.add("META-INF/*")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.androidx.navigation)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.dagger.hilt)

    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.instr)
}