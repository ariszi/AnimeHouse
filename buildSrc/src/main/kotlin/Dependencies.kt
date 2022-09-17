object Dependencies {

    private const val kotlinStandardLibrary =
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"

    private const val appCompactLibrary = "androidx.appcompat:appcompat:${Versions.appcompat}"

    private const val coreKTXLibrary = "androidx.core:core-ktx:${Versions.core_ktx}"

    private const val supportAnnotationLibrary =
        "com.android.support:support-annotations:${Versions.android_support_annotations}"

    private const val vmSaveStateLibrary =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle_viewmodel_savedstate}"

    private const val lifecycleRuntimeKTXLibrary =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_viewmodel_savedstate}"

    private const val jsrLibrary = "javax.annotation:jsr250-api:${Versions.annotation_jsr250_api}"

    private const val okHttpLibrary = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"

    private const val fragmentKTXLibrary = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    private const val loggingInterceptorLibrary =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    private const val apolloRuntimeLibrary =
        "com.apollographql.apollo:apollo-runtime:${Versions.apollo}"

    private const val apolloAndroidSupportLibrary =
        "com.apollographql.apollo:apollo-android-support:${Versions.apollo}"

    private const val apolloCoroutinesSupportLibrary =
        "com.apollographql.apollo:apollo-coroutines-support:${Versions.apollo}"

    private const val kotlinxCoroutinesCoreLibrary =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    private const val kotlinxCoroutinesAndroidLibrary =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    private const val constraintLayoutLibrary =
        "com.android.support.constraint:constraint-layout:${Versions.constraint_layout}"

    private const val recycleViewLibrary =
        "androidx.recyclerview:recyclerview:${Versions.recyclerview}"

    private const val glideLibrary = "com.github.bumptech.glide:glide:${Versions.glide}"

    private const val navigationLibrary =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"

    private const val navigationKTXLibrary =
        "androidx.navigation:navigation-ui:${Versions.nav_version}"

    private const val navigationSafeArgsLibrary =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.nav_version}"

    private const val navigationDynamicFeaturesLibrary =
        "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_version}"

    private const val navigationTestingLibrary =
        "androidx.navigation:navigation-testing:${Versions.nav_version}"

    private const val navigationComposeLibrary =
        "androidx.navigation:navigation-compose:${Versions.nav_version}"

    private const val timberLibrary = "com.jakewharton.timber:timber:${Versions.timber}"

    private const val lottieLibrary = "com.airbnb.android:lottie:${Versions.lottie}"

    private const val injectAssistedAnnotationDaggerLibrary =
        "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.assisted_inject_annotations_dagger}"

    private const val hiltAndroidLibrary = "com.google.dagger:hilt-android:${Versions.hilt_version}"

    private const val hiltKaptCompilerLibrary =
        "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"

    private const val jetBrainsAnnotationLibrary =
        "org.jetbrains:annotations:${Versions.jetbrains_annotation}"

    private const val glideCompilerLibrary = "com.github.bumptech.glide:compiler:${Versions.glide}"

    private const val junitLibrary = "junit:junit:${Versions.junit}"

    private const val androidxTestRunnerLibrary =
        "androidx.test:runner:${Versions.android_test_runner}"

    private const val espressoCoreLibrary =
        "androidx.test.espresso:espresso-core:${Versions.espresso_core}"

    private const val dataBindingLibrary =
        "androidx.databinding:databinding-runtime:${Versions.data_binding}"

    /* Right now the lists bellow are going to be formed with a single module approach.
    In the future those lists might take another formation according to the module they are used
    to.*/

    val appLibraries = arrayListOf<String>().apply {
        add(dataBindingLibrary)
        add(navigationLibrary)
        add(navigationComposeLibrary)
        add(navigationDynamicFeaturesLibrary)
     //   add(navigationSafeArgsLibrary)
        add(navigationKTXLibrary)
        add(kotlinStandardLibrary)
        add(appCompactLibrary)
        add(coreKTXLibrary)
        add(vmSaveStateLibrary)
        add(lifecycleRuntimeKTXLibrary)
        add(jsrLibrary)
        add(okHttpLibrary)
        add(fragmentKTXLibrary)
        add(loggingInterceptorLibrary)
        add(supportAnnotationLibrary)
        add(apolloRuntimeLibrary)
        add(apolloAndroidSupportLibrary)
        add(apolloCoroutinesSupportLibrary)
        add(kotlinxCoroutinesCoreLibrary)
        add(kotlinxCoroutinesAndroidLibrary)
        add(constraintLayoutLibrary)
        add(recycleViewLibrary)
        add(glideLibrary)
        add(timberLibrary)
        add(lottieLibrary)
        add(injectAssistedAnnotationDaggerLibrary)
        add(hiltAndroidLibrary)
    }

    val kaptLibraries = arrayListOf<String>().apply {
        add(hiltKaptCompilerLibrary)
    }

    val compileOnlyLibraries = arrayListOf<String>().apply {
        add(jetBrainsAnnotationLibrary)
    }

    val testCompileOnlyLibraries = arrayListOf<String>().apply {
        add(jetBrainsAnnotationLibrary)
    }

    val annotationProcessorLibraries = arrayListOf<String>().apply {
        add(glideCompilerLibrary)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(androidxTestRunnerLibrary)
        add(espressoCoreLibrary)
        add(navigationTestingLibrary)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junitLibrary)
    }

}
