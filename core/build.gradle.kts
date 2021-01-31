plugins {
    id ("com.android.library")
    id ("kotlin-android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Apps.compileSdk)
    defaultConfig {

        minSdkVersion(Apps.minSdk)
        targetSdkVersion(Apps.targetSdk)
        versionCode = Apps.versionCode
        versionName = Apps.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "$applicationId-v$versionName($versionCode)")

    }
    buildFeatures{
        viewBinding = true
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isDebuggable = false
            isZipAlignEnabled = true
            isJniDebuggable = false
            isRenderscriptDebuggable = false
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

dependencies {


    //kotlin
    implementation(Libs.kotlin)
    implementation(Libs.kotlinReflect)
    implementation(Libs.coreKtx)
    implementation(Libs.appcompat)
    implementation(Libs.constraintLayout)
    implementation(Libs.material)

    //Glide
    implementation(Libs.glide)
    kapt(Libs.glideCompilerKapt)

    //lifecycle
    implementation(Libs.lifecycleExtensions)
    implementation(Libs.lifecycleViewModel)
    implementation(Libs.lifecycleLifeData)
}