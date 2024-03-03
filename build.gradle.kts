import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.benmanes.gradle.versions.updates.gradle.GradleReleaseChannel
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.versions)
    `maven-publish`
    signing
}

tasks.withType<DependencyUpdatesTask> {
    gradleReleaseChannel = GradleReleaseChannel.CURRENT.id

    rejectVersionIf {
        listOf("alpha", "beta", "rc", "cr", "m", "eap", "pr", "dev").any {
            candidate.version.contains(it, ignoreCase = true)
        }
    }
}

kotlin {
    explicitApi()

    jvmToolchain(8)

    jvm()

    js(IR) {
        browser()
        nodejs()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        binaries.executable()
        nodejs()
    }

    /* https://kotlinlang.org/docs/native-target-support.html#tier-1 */

    macosX64()
    macosArm64()
    iosSimulatorArm64()
    iosX64()

    /* https://kotlinlang.org/docs/native-target-support.html#tier-2 */

    linuxX64()
    linuxArm64()

    watchosSimulatorArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()

    tvosSimulatorArm64()
    tvosX64()
    tvosArm64()

    iosArm64()

    /* https://kotlinlang.org/docs/native-target-support.html#tier-3 */

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()

    mingwX64()

    watchosDeviceArm64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.contracts.ExperimentalContracts")
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        jvmTest {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        jsTest {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

tasks.withType<Jar> {
    from(rootDir.resolve("LICENSE")) {
        into("META-INF")
    }
}

/* https://youtrack.jetbrains.com/issue/KT-63014/Running-tests-with-wasmJs-in-1.9.20-requires-Chrome-Canary#focus=Comments-27-8321383.0-0 */
rootProject.the<NodeJsRootExtension>().apply {
    nodeVersion = "21.0.0-v8-canary202309143a48826a08"
    nodeDownloadBaseUrl = "https://nodejs.org/download/v8-canary"
}

rootProject.tasks.withType<KotlinNpmInstallTask> {
    args.add("--ignore-engines")
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
