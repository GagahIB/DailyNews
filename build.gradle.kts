// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("http://oss.jfrog.org/artifactory/oss-snapshot-local/") }
        maven { url = uri("https://maven.google.com") }
        maven {url = uri("https://plugins.gradle.org/m2/")}
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navVersion}")
//        classpath ("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:${Versions.sonarqube}")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://maven.google.com")}
        maven { url = uri("https://jitpack.io") }

    }
}




tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}