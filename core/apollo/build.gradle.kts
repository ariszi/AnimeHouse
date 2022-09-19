apply {
    from("$rootDir/android-library-build.gradle")
}
plugins {
    id("kotlin-android")
    id("com.android.library")
    id("com.apollographql.apollo3").version(Versions.apollo)
    id("kotlin-kapt")
}


repositories {
    mavenCentral()
}

apollo {
    generateKotlinModels.set(true)
    packageName.set("zisis.aristofanis.animehouse")
    schemaFile.set(file(ApolloConfig.schemaFile))
}

dependencies {
    api(Libs.apolloRuntimeLibrary)
    api(Libs.apolloCacheSupportLibrary)
    api(Libs.apolloApiLibrary)

}

