@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    kotlin("kapt")
}


android {
    namespace = libs.versions.packageName.get() + ".core.di"
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
    implementation(project(":presentation:home"))
    implementation(project(":core:common"))
    implementation(project(":domain:sport_events:usecases"))
    implementation(project(":data:sport_events:repo"))
    implementation(project(":data:sport_events:source-db"))
    implementation(project(":data:sport_events:source-net"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(libs.logging.timber)
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

}

kapt {
    correctErrorTypes = true
}
