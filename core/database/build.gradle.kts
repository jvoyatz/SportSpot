@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}

android {
    namespace = libs.versions.packageName.get() + "core.database"
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
        resources.merges.add("META-INF/gradle/incremental.annotation.processors")
    }
}

dependencies {
    implementation(project(":core:testing"))
    implementation(libs.bundles.room)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    androidTestImplementation(libs.room.testing)

    implementation(libs.bundles.common.android)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.instr)
    androidTestImplementation(libs.coroutines.test)

    androidTestImplementation("app.cash.turbine:turbine:0.9.0")
    implementation(libs.moshi)
    implementation(libs.moshi.codegen)
}

kapt {
    correctErrorTypes = true
}
