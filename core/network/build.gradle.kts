@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.keetr.comicsnac.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
//    implementation(libs.appcompat)
//    implementation(libs.material)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.core)
    implementation(libs.ktor.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.ktor.mock)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.androidx.test.ext.junit)
//    androidTestImplementation(libs.androidx.test.espresso.core)
}