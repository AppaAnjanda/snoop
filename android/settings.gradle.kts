import java.net.URI

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = URI("https://jitpack.io")
//            url = URI("https://devrepo.kakao.com/nexus/content/groups/public/")
        }
        maven{ url = URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "snoop"
include(":app")
include(":presentation")
include(":data")
include(":domain")
