object Apps {
    const val applicationId = "id.gagahib.newsapi"
    const val compileSdk = 29
    const val minSdk = 19
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    const val gradle = "4.0.1"
    const val kotlin = "1.3.72"
    const val appcompat = "1.2.0"
    const val coreKtx = "1.3.2"
    const val constraintLayout = "2.0.4"
    const val material = "1.3.0-alpha01"
    const val swiperefreshlayout = "1.1.0"
    const val navVersion = "2.1.0"
    const val glide = "4.9.0"
    const val dagger = "2.27"
    const val daggerHilt = "2.28-alpha"
    const val hiltCompiler = "1.0.0-alpha02"
    const val retrofit = "2.9.0"
    const val moshi = "1.9.3"
    const val okhttp = "4.7.2"
    const val coroutinesCore = "1.3.7"
    const val lifecycleExtensions = "2.2.0"
    const val lifecycleKtx = "2.3.0-alpha04"
    const val multidex = "2.0.1"
    const val multipleStatusView = "1.7"
    const val sonarqube = "3.1.1"

    /* test */
    const val junit = "4.12"
    const val espresso = "3.2.0"
    const val extJunit = "1.1.2"
    const val espressoCore = "3.3.0"
}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompilerKapt = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerKapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerProcessorKapt = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    const val daggerHiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
    const val hilt = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltCompiler}"
    const val hiltKapt = "androidx.hilt:hilt-compiler:1.0.0-alpha02${Versions.hiltCompiler}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiCodegenKapt = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutinesCoreCommon = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutinesCore}"

    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleKtx}"
    const val lifecycleLifeData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleKtx}"

    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    const val multipleStatusView = "com.classic.common:multiple-status-view:${Versions.multipleStatusView}"

}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val espresso = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"

}