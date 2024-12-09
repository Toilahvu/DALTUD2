pluginManagement {
    repositories {
        google() // Kho Google cho Android plugin và dependencies
        mavenCentral() // Kho Maven Central
        gradlePluginPortal() // Kho Gradle Plugin Portal
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() // Kho Google
        mavenCentral() // Kho Maven Central
        // Nếu sử dụng JitPack hoặc kho khác, thêm tại đây:
        // maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "DALTUD2"
include(":app")
