@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
//not needed since 7.4
//enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "SportSpot"
include(":app")
include(":core:testing")
include(":core:common")
include(":core:network")
include(":domain:sport_events:model")
include(":domain:sport_events:usecases")
include(":data:sport_events:repo")
include(":data:sport_events:source-net")
include(":data:sport_events:source-db")
include(":core:common-testing")
include(":core:database")
include(":core:di")
include(":presentation:home")
include(":core:common-android")
