import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.pathString

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

val projects: Path = Path (
        System.getenv("HOME"),
        "Proyectos",
        "Propios",
        "Android"
)

val wipProjects: Path = Path (
        projects.toFile().absolutePath,
        "_WIP"
)

rootProject.name = "Helpers"
include(":app")
include (":lib")

include (":Networking")
include (":Serialization")
include (":Scheduling")
include (":Threading")
include (":Helpers-Utils")
include (":UI")

/*
include (":UIHelpers")
project (":UIHelpers").projectDir = File (
        wipProjects.toFile(),
        "UIHelpers/lib"
)
*/
