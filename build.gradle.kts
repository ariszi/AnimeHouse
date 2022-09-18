// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Libs.androidToolsBuildGradleLibrary)
        classpath(Libs.kotlinGradleGradlePluginLibrary)
        classpath(Libs.apolloGradlePluginLibrary)
        classpath(Libs.hiltAndroidGradlePluginLibrary)
        classpath(Libs.navSafeArgsGradlePluginLibrary)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
