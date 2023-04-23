plugins {
    id("anime.house.android.library")
    id("anime.house.di")
    id("com.apollographql.apollo3").version("3.8.1")
}

apollo {
    generateKotlinModels.set(true)
    packageName.set("zisis.aristofanis.animehouse")
    introspection{
        endpointUrl.set("https://graphql.anilist.co/graphql")
    }
}

dependencies {
    implementation(libs.apollo.runtime)
    implementation(libs.timber)
    implementation(libs.gson)
    implementation(libs.okhttp.logging)
}
android {
    namespace = "zi.aris.network"
}

