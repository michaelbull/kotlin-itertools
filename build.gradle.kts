import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.benmanes.gradle.versions.updates.gradle.GradleReleaseChannel
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.versions)
    alias(libs.plugins.maven.publish)
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

    compilerOptions {
        optIn.add("kotlin.contracts.ExperimentalContracts")
    }

    jvm()
    jvmToolchain(8)

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
    manifest {
        attributes(mapOf("SPDX-License-Identifier" to "ISC"))
    }

    from(rootDir.resolve("LICENSE")) {
        into("META-INF")
    }
}


mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Empty(),
            sourcesJar = true,
        )
    )

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
