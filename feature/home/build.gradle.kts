plugins {
    id("comicsnac.android.feature")
}

android {
    namespace = "com.keetr.comicsnac.home"
}
dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:ui"))
}
