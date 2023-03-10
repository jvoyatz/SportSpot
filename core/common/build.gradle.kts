@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}


java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies{
    implementation(libs.coroutines)
    implementation(libs.javax.inject)
    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.coroutines.test)
}
