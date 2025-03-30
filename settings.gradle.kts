pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    // 🚫 versionCatalogs eliminado porque ya se aplica automáticamente
}

rootProject.name = "VoleyStats"
include(":app")
