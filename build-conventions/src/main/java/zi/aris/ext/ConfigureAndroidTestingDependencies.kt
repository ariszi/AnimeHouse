package zi.aris.ext

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.configureAndroidTestingDependencies() {

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        add("testRuntimeOnly", libs.findLibrary("test.junit5.engine").get())
        add("testImplementation", libs.findLibrary("test.junit5.api").get())
        add("androidTestImplementation", libs.findLibrary("test.android.core").get())
    }
}
