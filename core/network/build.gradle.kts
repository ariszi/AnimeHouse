apply {
    from("$rootDir/android-library-build.gradle")
}
plugins {
    id("kotlin-android")
    id("com.android.library")
    id("com.apollographql.apollo")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {

    implementation(project(Modules.coreKotlinUtils))
    api(Libs.apolloRuntimeLibrary)
    api(Libs.apolloAndroidSupportLibrary)
    api(Libs.apolloCoroutinesSupportLibrary)
    api(Libs.hiltAndroidLibrary)
    api(Libs.injectAssistedAnnotationDaggerLibrary)
    api(Libs.loggingInterceptorLibrary)

    kapt(Libs.hiltKaptCompilerLibrary)

}

apollo {
    schemaFile.set(file(ApolloConfig.schemaFile))
    rootPackageName.set(AppConfig.appId)
    customTypeMapping.put(ApolloConfig.customTypeMappingKey, ApolloConfig.customTypeMappingValue)
}
