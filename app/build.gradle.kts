plugins {
    id("com.apollographql.apollo")
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}


android {

    compileSdk = AppConfig.compileSdk
    buildToolsVersion = AppConfig.buildToolsVersion

    defaultConfig {
        applicationId = "zisis.aristofanis.animehouse"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(AppConfig.proguardDefaultFile),
                AppConfig.proguardConsumerRules
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}
kapt {
    correctErrorTypes = true
}

apollo {
    schemaFile.set(file("../app/src/main/graphql/schema.json"))
    rootPackageName.set("zisis.aristofanis.animehouse")
    customTypeMapping.put("Date", "java.util.Date")
}

dependencies {
    //std lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //app libs
    implementation(Dependencies.appLibraries)
    kapt(Dependencies.kaptLibraries)
    compileOnly(Dependencies.compileOnlyLibraries)
    testCompileOnly(Dependencies.testCompileOnlyLibraries)
    annotationProcessor(Dependencies.annotationProcessorLibraries)
    //test libs
    testImplementation(Dependencies.testLibraries)
    androidTestImplementation(Dependencies.androidTestLibraries)
}
