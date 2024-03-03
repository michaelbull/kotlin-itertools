import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    `maven-publish`
    signing
}

publishing {
    repositories {
        maven {
            if (project.version.toString().endsWith("SNAPSHOT")) {
                setUrl("https://oss.sonatype.org/content/repositories/snapshots")
            } else {
                setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            }

            credentials {
                val ossrhUsername: String? by project
                val ossrhPassword: String? by project

                username = ossrhUsername
                password = ossrhPassword
            }
        }
    }

    publications.withType<MavenPublication> {
        val targetName = this@withType.name

        artifact(tasks.register("${targetName}JavadocJar", Jar::class) {
            group = LifecycleBasePlugin.BUILD_GROUP
            description = "Assembles a jar archive containing the Javadoc API documentation of target '$targetName'."
            archiveClassifier.set("javadoc")
            archiveAppendix.set(targetName)
        })

        pom {
            name.set(project.name)
            description.set(project.description)
            url.set("https://github.com/michaelbull/kotlin-itertools")
            inceptionYear.set("2024")

            licenses {
                license {
                    name.set("ISC License")
                    url.set("https://opensource.org/licenses/isc-license.txt")
                }
            }

            developers {
                developer {
                    name.set("Michael Bull")
                    url.set("https://www.michael-bull.com")
                }
            }

            scm {
                connection.set("scm:git:https://github.com/michaelbull/kotlin-itertools")
                developerConnection.set("scm:git:git@github.com:michaelbull/kotlin-itertools.git")
                url.set("https://github.com/michaelbull/kotlin-itertools")
            }

            issueManagement {
                system.set("GitHub Issues")
                url.set("https://github.com/michaelbull/kotlin-itertools/issues")
            }

            ciManagement {
                system.set("GitHub Actions")
                url.set("https://github.com/michaelbull/kotlin-itertools/actions")
            }
        }
    }
}

signing {
    val signingKeyId: String? by project // must be the last 8 digits of the key
    val signingKey: String? by project
    val signingPassword: String? by project

    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications)
}
