import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.nant
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.03"

project {

    vcsRoot(HttpsGithubComPalfreyNAntScriptGitRefsHeadsMaster)

    buildType(NAntScript)
    buildType(NAntGraph)
}

object NAntGraph : BuildType({
    name = "✅ NAnt Graph"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        nant {
            id = "NAnt"
            mode = nantFile {
                path = "nantgraph.build"
            }
            nantHome = """C:\nant-0.92\bin"""
            targets = "build test.samples"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object NAntScript : BuildType({
    name = "✅NAnt Script"

    params {
        param("env.NAntHome", """C:\Users\Administrator\Downloads\nant-0.92-bin\nant-0.92\bin\NAnt.exe""")
    }

    vcs {
        root(HttpsGithubComPalfreyNAntScriptGitRefsHeadsMaster)
    }

    steps {
        nant {
            id = "NAnt"
            mode = nantFile {
                path = "default.build"
            }
            nantHome = """C:\nant-0.92\bin"""
            targets = "debug build"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object HttpsGithubComPalfreyNAntScriptGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/palfrey/NAntScript.git#refs/heads/master"
    url = "https://github.com/palfrey/NAntScript.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = password {
        password = "credentialsJSON:fdff7e7f-af49-4535-bd20-2d3e4e39da83"
    }
})