plugins {
    id("anime.house.android.application")
    id("anime.house.di")
}

android {
    namespace = "zisis.aristofanis.animehouse"
    compileSdk = 33

    defaultConfig {
        applicationId = "zisis.aristofanis.animehouse"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                "proguard-android-optimize.txt", "proguard-rules.pro"
            )
        }
    }
    packagingOptions {
        resources.excludes.add("META-INF/**")
        resources.excludes.add("**/attach_hotspot_windows.dll")
    }
}

dependencies {

    implementation(libs.app.compat)
    implementation(libs.core.ktx)
    implementation(libs.saved.state)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.constraint.layout)
    implementation(libs.lottie)
    implementation(libs.apollo.runtime)
    implementation(libs.glide)
    implementation(libs.recycler.view)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui)
    implementation(libs.hilt.navigation)
    implementation(project(":network"))

}
