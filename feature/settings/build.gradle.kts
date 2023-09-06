plugins {
    id("comicsnac.android.feature")
}

android {
    namespace = "com.keetr.comicsnac.settings"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.browser)
}