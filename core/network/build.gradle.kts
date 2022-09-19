apply {
    from("$rootDir/android-library-build.gradle")
}
plugins {
    id("kotlin-android")
    id("com.android.library")
    id("kotlin-kapt")
}

dependencies {

    implementation(project(Modules.coreKotlinUtils))
    api(project(Modules.coreApollo))
    api(Libs.loggingInterceptorLibrary)
    implementation(Libs.hiltAndroidLibrary)
    kapt(Libs.hiltKaptCompilerLibrary)

}

