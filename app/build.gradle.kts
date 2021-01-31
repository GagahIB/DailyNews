plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
//    id("org.sonarqube") //version 3.1.1
}

android {
    compileSdkVersion(Apps.compileSdk)
    defaultConfig {

        applicationId = Apps.applicationId
        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode = Apps.versionCode
        versionName = Apps.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "$applicationId-v$versionName($versionCode)")
        resConfigs("en")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
    }
    bundle {
        language { enableSplit = true }
        density { enableSplit = true }
        abi { enableSplit = true }
    }
    lintOptions {
        isAbortOnError = false
        isIgnoreWarnings = true
        isQuiet = true
    }
    packagingOptions {
        exclude("META-INF/notice.txt")
    }
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "API_BASE", "\"http://newsapi.org/v2/\"")
            isMinifyEnabled = false
//            isDebuggable = true
            versionNameSuffix = "-dev"
        }

        getByName("release") {
            buildConfigField("String", "API_BASE", "\"http://newsapi.org/v2/\"")
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            isZipAlignEnabled = true
            isJniDebuggable = false
            isRenderscriptDebuggable = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    compileOptions {
        sourceCompatibility  = JavaVersion.VERSION_1_8
        targetCompatibility  = JavaVersion.VERSION_1_8
    }
}
//
//sonarqube {
//    properties {
//        properties sonar.sourceEncoding = "UTF-8"
//        properties sonar.projectName =  "Scan using SonarQube Scanner"
//    }
//}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))


    implementation(project(":core"))

    //kotlin
    implementation(Libs.kotlin)
    implementation(Libs.kotlinReflect)
    implementation(Libs.coreKtx)
    implementation(Libs.appcompat)
    implementation(Libs.constraintLayout)
    implementation(Libs.material)
    implementation(Libs.swiperefreshlayout)
    implementation(Libs.navigationFragment)
    implementation(Libs.navigationUI)

    //Glide
    implementation(Libs.glide)
    kapt(Libs.glideCompilerKapt)

    //Dagger
    implementation(Libs.dagger)
    implementation(Libs.daggerAndroid)
    implementation(Libs.daggerAndroidSupport)
    kapt(Libs.daggerKapt)
    kapt(Libs.daggerProcessorKapt)

    //retrofit
    implementation(Libs.retrofit)
    implementation(Libs.converterGson)
    implementation(Libs.converterMoshi)
    implementation(Libs.moshi)
    implementation(Libs.okhttp)
    implementation(Libs.logging)
    kapt(Libs.moshiCodegenKapt)

    //coroutines
    implementation(Libs.coroutinesCore)
    implementation(Libs.coroutinesCoreCommon)

    //lifecycle
    implementation(Libs.lifecycleExtensions)
    implementation(Libs.lifecycleViewModel)
    implementation(Libs.lifecycleLifeData)

    //MultiDex
    implementation(Libs.multidex)

    //Multiple Status View
    implementation(Libs.multipleStatusView)


    //testing libs
    implementation(TestLibs.espresso)
    testImplementation(TestLibs.junit)
    androidTestImplementation(TestLibs.extJunit)
    androidTestImplementation(TestLibs.espressoCore)

}