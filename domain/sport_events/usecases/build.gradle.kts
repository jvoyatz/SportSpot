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
    implementation(project(":core:common"))
    implementation(project(":domain:sport_events:model"))
    implementation(libs.coroutines)
    implementation(libs.javax.inject)
}