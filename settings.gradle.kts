pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        includeBuild("build-logic")
        google()
        mavenCentral()
    }
}

rootProject.name = "Comic Snac"
include(":app")
include(":core:data")
include(":core:model")
include(":core:network")
include(":core:ui")
include(":feature:categories")
include(":feature:details")
include(":feature:home")
include(":feature:search")
include(":baselineprofile")
