@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}


android {
    namespace = libs.versions.packageName.get() + ".data.sports_events.source.db"
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
}

dependencies {
    implementation(project(":core:database"))

    implementation(libs.bundles.common.android)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    testImplementation(project(":core:testing"))
    testImplementation(libs.bundles.testing.unit)

}

kapt {
    correctErrorTypes = true
}
