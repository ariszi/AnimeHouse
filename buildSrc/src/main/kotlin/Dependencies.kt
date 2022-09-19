object Libs {

    const val kotlinStandardLibrary =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"

    const val appCompactLibrary = "androidx.appcompat:appcompat:${Versions.appcompat}"

    const val coreKTXLibrary = "androidx.core:core-ktx:${Versions.core_ktx}"

    const val supportAnnotationLibrary =
        "com.android.support:support-annotations:${Versions.android_support_annotations}"

    const val vmSaveStateLibrary =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle_viewmodel_savedstate}"

    const val lifecycleRuntimeKTXLibrary =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_viewmodel_savedstate}"

    const val jsrLibrary = "javax.annotation:jsr250-api:${Versions.annotation_jsr250_api}"

    const val okHttpLibrary = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"

    const val fragmentKTXLibrary = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    const val loggingInterceptorLibrary =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val apolloRuntimeLibrary =
        "com.apollographql.apollo3:apollo-runtime:${Versions.apollo}"

    const val apolloAndroidSupportLibrary =
        "com.apollographql.apollo3:apollo-android-support:${Versions.apollo}"

    const val apolloCoroutinesSupportLibrary =
        "com.apollographql.apollo3:apollo-coroutines-support:${Versions.apollo}"

    const val apolloCacheSupportLibrary =
        "com.apollographql.apollo3:apollo-normalized-cache-sqlite:${Versions.apollo}"

    const val apolloApiLibrary =
        "com.apollographql.apollo3:apollo-api:${Versions.apollo}"

    const val kotlinxCoroutinesCoreLibrary =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    const val kotlinxCoroutinesAndroidLibrary =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val constraintLayoutLibrary =
        "com.android.support.constraint:constraint-layout:${Versions.constraint_layout}"

    const val recycleViewLibrary =
        "androidx.recyclerview:recyclerview:${Versions.recyclerview}"

    const val glideLibrary = "com.github.bumptech.glide:glide:${Versions.glide}"

    const val navigationLibrary =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"

    const val navigationKTXLibrary =
        "androidx.navigation:navigation-ui:${Versions.nav_version}"

    const val navigationDynamicFeaturesLibrary =
        "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_version}"

    const val navigationTestingLibrary =
        "androidx.navigation:navigation-testing:${Versions.nav_version}"

    const val navigationComposeLibrary =
        "androidx.navigation:navigation-compose:${Versions.nav_version}"

    const val timberLibrary = "com.jakewharton.timber:timber:${Versions.timber}"

    const val lottieLibrary = "com.airbnb.android:lottie:${Versions.lottie}"

    const val injectAssistedAnnotationDaggerLibrary =
        "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.assisted_inject_annotations_dagger}"

    const val hiltAndroidLibrary = "com.google.dagger:hilt-android:${Versions.hilt_version}"

    const val hiltKaptCompilerLibrary =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"

    const val jetBrainsAnnotationLibrary =
        "org.jetbrains:annotations:${Versions.jetbrains_annotation}"

    const val glideCompilerLibrary = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val junitLibrary = "junit:junit:${Versions.junit}"

    const val androidxTestRunnerLibrary =
        "androidx.test:runner:${Versions.android_test_runner}"

    const val espressoCoreLibrary =
        "androidx.test.espresso:espresso-core:${Versions.espresso_core}"

    const val dataBindingLibrary =
        "androidx.databinding:databinding-runtime:${Versions.data_binding}"


    const val androidToolsBuildGradleLibrary = "com.android.tools.build:gradle:${Versions.gradle}"

    const val kotlinGradleGradlePluginLibrary =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"

    const val apolloGradlePluginLibrary =
        "com.apollographql.apollo3:apollo-gradle-plugin:${Versions.apollo}"

    const val hiltAndroidGradlePluginLibrary =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_version}"

    const val navSafeArgsGradlePluginLibrary =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav_version}"


}

object AppConfig {

    const val compileSdk = 33
    const val minSdk = 21
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0"
    const val buildToolsVersion = "31.0.0"
    const val appId = "zisis.aristofanis.animehouse"

    const val androidTestInstrumentation = "androidx.test.runner.AndroidJUnitRunner"
    const val proguardConsumerRules = "consumer-rules.pro"
    const val proguardDefaultFile = "proguard-android-optimize.txt"
    const val dimension = "environment"

}

object ApolloConfig {
    const val schemaFile = "../apollo/src/main/graphql/schema.json"
    const val customTypeMappingKey = "Date"
    const val customTypeMappingValue = "java.util.Date"

}

object Versions {

    const val gradle = "7.3.0"
    const val kotlin_version = "1.6.0"
    const val hilt_version = "2.43.2"
    const val nav_version = "2.5.2"
    const val data_binding = "7.3.0"
    const val appcompat = "1.4.1"
    const val core_ktx = "1.9.0"
    const val fragment = "1.5.2"
    const val android_support_annotations = "28.0.0"
    const val lifecycle_viewmodel_savedstate = "2.5.1"
    const val annotation_jsr250_api = "1.0"
    const val okhttp = "4.9.3"
    const val apollo = "3.6.0"
    const val coroutines = "1.6.4"
    const val constraint_layout = "2.1.3"
    const val recyclerview = "1.2.1"
    const val glide = "4.12.0"
    const val timber = "4.7.1"
    const val lottie = "5.0.3"
    const val jetbrains_annotation = "16.0.1"
    const val assisted_inject_annotations_dagger = "0.8.1"
    const val junit = "5.8.2"
    const val android_test_runner = "1.4.0"
    const val espresso_core = "3.4.0"

}

object Modules {

    const val app = ":app"

    const val core = ":core"

    const val featureAnimeCommon = ":feature:anime-common"

    const val featureAnimeList = ":feature:animes-list"

    const val featureAnimeDetails = ":feature:anime-details"
    const val coreDesignSystem = ":core:design-system"
    const val coreNetwork = ":core:network"
    const val coreApollo = ":core:apollo"
    const val coreNavigation = ":core:navigation"
    const val coreKotlinUtils = ":core:kotlin-utils"
    const val testCommon = "test:common"

}

