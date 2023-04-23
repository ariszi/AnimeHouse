dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

pluginManagement {
    includeBuild("build-conventions")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

include(":app")
rootProject.name="Anime House"

// Features
include(":feature:anime-common")
include(":feature:animes-list")
include(":feature:anime-details")
// Core
include(":design-system")
include(":network")
include(":navigation")
include(":kotlin-utils")
// Testing
include("test:common")
