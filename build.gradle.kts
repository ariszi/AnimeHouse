// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}")
        classpath("com.apollographql.apollo:apollo-gradle-plugin:${Versions.apollo}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
//        maven {
//            url = "https://dl.bintray.com/apollographql/android"
//        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
