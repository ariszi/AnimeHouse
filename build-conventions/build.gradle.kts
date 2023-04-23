plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

gradlePlugin {
    plugins {
        register("application_convention_plugin") {
            id = "anime.house.android.application"
            implementationClass = "zi.aris.conventions.ApplicationConventionPlugin"
        }
        register("androidLibrary_convention_plugin") {
            id = "anime.house.android.library"
            implementationClass = "zi.aris.conventions.AndroidLibraryConventionPlugin"
        }
        register("app_dependency_injection") {
            id = "anime.house.di"
            implementationClass = "zi.aris.conventions.DIConventionPlugin"
        }
    }
}


