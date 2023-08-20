plugins {
    id("comicsnac.android.library")
    id("comicsnac.android.hilt")
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.keetr.comicsnac.database"

//    compileOptions {
//        isCoreLibraryDesugaringEnabled = true
//    }
}

dependencies {

    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    testImplementation(libs.androidx.room.testing)
}