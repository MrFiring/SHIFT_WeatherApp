plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.symbolProcessing)
    alias(libs.plugins.kotlin.serialization)
}

android {
    defaultConfig {
        applicationId = "ru.mrfiring.shiftweatherapp"
        minSdk = 26
        targetSdk = 36
        compileSdk = 36
        versionCode = 3
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    namespace = "ru.mrfiring.shiftweatherapp"
}

dependencies {
    implementation(libs.kotlin)

    implementation(libs.playServices.location)

    //Testing implementations
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)

    //Retrofit and Moshi
    implementation(libs.retrofit)
    implementation(libs.retrofit.converterMoshi)
    implementation(libs.moshi)
    implementation(libs.moshiKotlin)
    ksp(libs.moshiKotlinCodegen)

    //Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)

    // coroutines for getting off the UI thread
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Decompose
    implementation(libs.decompose)
    implementation(libs.decompose.extensionsCompose)
    implementation(libs.decompose.lifecycleCoroutines)

    // Paging
    implementation(libs.paging.runtimeKtx)
    implementation(libs.paging.compose)

    //Koin
    implementation(libs.koinAndroid)
    // Koin AndroidX Jetpack Compose
    implementation(libs.koinAndroid.compose)

    ///Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.uiTooling)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.activity)
    implementation(libs.compose.compiler)

    implementation(libs.accompanist.permissions)
    implementation(libs.splashscreen)
}