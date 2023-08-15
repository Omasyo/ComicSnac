import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                compileSdk = 34

                defaultConfig {
                    minSdk = 24

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }
                tasks.withType<KotlinCompile>().configureEach {
                    kotlinOptions {
                        jvmTarget = "17"
                    }
                }

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                dependencies {
                    "implementation"(libs.findLibrary("androidx.core.ktx").get())

                    "androidTestImplementation"(
                        libs.findLibrary("androidx.test.ext.junit.ktx").get()
                    )
//                    "androidTestImplementation"(libs.findLibrary("androidx.test.runner").get())

                    "testImplementation"(libs.findLibrary("junit").get())
                    "testImplementation"(libs.findLibrary("mockk").get())
                    "testImplementation"(libs.findLibrary("kotlinx.coroutines.test").get())
                }
            }
        }
    }
}