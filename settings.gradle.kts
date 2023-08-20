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
include(":core:ui")
include(":core:network")
include(":core:model")
include(":feature:home")
include(":core:data")
include(":feature:details")
include(":core:database")
