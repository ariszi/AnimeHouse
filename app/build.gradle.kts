plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
    kotlin("android")
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
    buildFeatures {
        viewBinding = true
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


kapt {
    correctErrorTypes = true
}



dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.dataBindingLibrary)
    implementation(Libs.navigationLibrary)
    implementation(Libs.navigationComposeLibrary)
    implementation(Libs.navigationDynamicFeaturesLibrary)
    implementation(Libs.navigationKTXLibrary)
    implementation(Libs.appCompactLibrary)
    implementation(Libs.coreKTXLibrary)
    implementation(Libs.vmSaveStateLibrary)
    implementation(Libs.lifecycleRuntimeKTXLibrary)
    implementation(Libs.jsrLibrary)
    implementation(Libs.okHttpLibrary)
    implementation(Libs.fragmentKTXLibrary)
    implementation(Libs.supportAnnotationLibrary)
    implementation(Libs.kotlinxCoroutinesCoreLibrary)
    implementation(Libs.kotlinxCoroutinesAndroidLibrary)
    implementation(Libs.constraintLayoutLibrary)
    implementation(Libs.recycleViewLibrary)
    implementation(Libs.glideLibrary)
    implementation(Libs.timberLibrary)
    implementation(Libs.lottieLibrary)

    implementation(project(Modules.coreNetwork))

    //std lib

    //app libs
    compileOnly(Libs.jetBrainsAnnotationLibrary)
    annotationProcessor(Libs.glideCompilerLibrary)
    //test libs
    testImplementation(Libs.junitLibrary)
    androidTestImplementation(Libs.androidxTestRunnerLibrary)
    androidTestImplementation(Libs.espressoCoreLibrary)
    androidTestImplementation(Libs.navigationTestingLibrary)


}
