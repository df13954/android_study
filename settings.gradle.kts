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
        google()
        mavenCentral()
        maven("https://api.xposed.info/")
        maven("https://maven.aliyun.com/repository/google")
        maven {
            setUrl("https://maven.google.com")
        }
        maven { setUrl("https://jitpack.io")}
    }
}

rootProject.name = "thouger"
include(":app")
include(":hook")
