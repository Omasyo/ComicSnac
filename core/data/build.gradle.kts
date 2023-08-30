plugins {
    id("comicsnac.android.library")
    id("comicsnac.android.hilt")
}

android {
    namespace = "com.keetr.comicsnac.data"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {

    coreLibraryDesugaring(libs.android.tools.desugar)

    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.androidx.paging.compose)
}