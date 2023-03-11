@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = libs.versions.packageName.get() + ".data.sports_events.repo"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        buildConfigField("String", "API_ENDPOINT", "\"https://618d3aa7fe09aa001744060a.mockapi.io/api/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
}

dependencies {
    implementation(project(":domain:sport_events:model"))
    implementation(project(":domain:sport_events:usecases"))
    implementation(project(":data:sport_events:source-net"))
    implementation(project(":data:sport_events:source-db"))
    implementation(libs.bundles.common.android)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.instr)
    implementation(libs.bundles.networking)
}