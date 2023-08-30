plugins {
    id("comicsnac.android.library")
    id("comicsnac.android.compose")
}

android {
    namespace = "com.keetr.comicsnac.ui"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.accompanist.webview)
}