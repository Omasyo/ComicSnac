plugins {
    id("comicsnac.android.library")
    id("comicsnac.android.hilt")
}

android {
    namespace = "com.keetr.comicsnac.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))

    implementation(libs.androidx.paging.compose)
}