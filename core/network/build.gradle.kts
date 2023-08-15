plugins {
    id("comicsnac.android.library")
    id("comicsnac.android.hilt")
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.keetr.comicsnac.network"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {

    coreLibraryDesugaring(libs.android.tools.desugar)

    implementation(libs.ktor.cio)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.core)
    implementation(libs.ktor.serialization.json)

    testImplementation(libs.ktor.mock)
}