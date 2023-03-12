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
        testInstrumentationRunner = libs.versions.androidTestInstrumentation.get()
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
    packagingOptions {
        resources.merges.add("META-INF/*")
    }
}

dependencies {
    implementation(project(":core:database"))

    implementation(libs.moshi)
    implementation(libs.moshi.codegen)
    implementation(libs.logging.timber)

    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

    androidTestImplementation(project(":core:testing"))
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.room.testing)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.instr)
    androidTestImplementation(libs.coroutines.test)

}

kapt {
    correctErrorTypes = true
}
