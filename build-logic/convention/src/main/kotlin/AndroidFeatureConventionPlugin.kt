import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("comicsnac.android.library")
                apply("comicsnac.android.compose")
                apply("comicsnac.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                compileOptions {
                    isCoreLibraryDesugaringEnabled = true
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {

                "coreLibraryDesugaring"(libs.findLibrary("android.tools.desugar").get())

                "implementation"(project(":core:data"))
                "implementation"(project(":core:model"))
                "implementation"(project(":core:ui"))

                "implementation"(libs.findLibrary("androidx.hilt.navigation.compose").get())
                "implementation"(libs.findLibrary("androidx.navigation.compose").get())

                "implementation"(libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
                "implementation"(libs.findLibrary("androidx.paging.compose").get())
            }
        }
    }

}