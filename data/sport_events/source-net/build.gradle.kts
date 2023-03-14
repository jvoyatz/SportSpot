@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}


android {
    namespace = libs.versions.packageName.get() + ".data.sports_events.source.net"
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
}

dependencies {
    implementation(project(":core:network"))

    implementation(libs.logging.timber)
    implementation(libs.dagger.hilt.android)

    implementation(libs.bundles.networking)

    kapt(libs.dagger.hilt.compiler)
    kapt(libs.moshi.codegen)

    testImplementation(project(":core:testing"))
    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.okhttp.mockwebserver)
    androidTestImplementation(libs.bundles.testing.common.android)
}

kapt {
    correctErrorTypes = true
}
