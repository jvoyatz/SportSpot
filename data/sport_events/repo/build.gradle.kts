@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = libs.versions.packageName.get() + ".data.sports_events.repo"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
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
    implementation(project(":core:common"))
    implementation(project(":core:testing"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":domain:sport_events:model"))
    implementation(project(":domain:sport_events:usecases"))
    implementation(project(":data:sport_events:source-net"))
    implementation(project(":data:sport_events:source-db"))

    implementation(libs.logging.timber)
    implementation(libs.javax.inject)
    implementation(libs.coroutines)

    testImplementation(libs.bundles.testing.unit)
    androidTestImplementation(libs.bundles.testing.instr)
}