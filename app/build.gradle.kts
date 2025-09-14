plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    defaultConfig {
        applicationId = "ru.mrfiring.shiftweatherapp"
        minSdk = 26
        targetSdk = 35
        compileSdk = 35
        versionCode = 2
        versionName = "1.0.1"

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

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")

    implementation("com.google.android.gms:play-services-location:21.3.0")

    //Testing implementations
    testImplementation("io.mockk:mockk:${rootProject.extra["version_mockk"]}")
    testImplementation("junit:junit:${rootProject.extra["version_junit"]}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra["version_coroutines_test"]}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.extra["version_core_testing"]}")

    //Retrofit and Moshi
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi:1.15.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    //Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    kapt("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-paging:${rootProject.extra["room_version"]}")
    // coroutines for getting off the UI thread
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra["version_coroutines"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra["version_coroutines"]}")

    // Decompose
    implementation("com.arkivanov.decompose:decompose:${rootProject.extra["version_decompose"]}")
    implementation("com.arkivanov.decompose:extensions-compose:${rootProject.extra["version_decompose_ext"]}")
    implementation("com.arkivanov.essenty:lifecycle-coroutines:${rootProject.extra["version_decompose_lifecycle"]}")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:${rootProject.extra["version_paging"]}")
    implementation("androidx.paging:paging-compose:3.2.1")

    //Koin
    implementation("io.insert-koin:koin-android:${rootProject.extra["version_koin"]}")
    // Koin AndroidX Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose:${rootProject.extra["version_koin"]}")


    ///Compose
    implementation("androidx.compose.ui:ui:${rootProject.extra["version_compose"]}")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:${rootProject.extra["version_compose"]}")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:${rootProject.extra["version_compose"]}")
    // Material Design
    implementation("androidx.compose.material:material:${rootProject.extra["version_compose"]}")
    // Material design icons
    implementation("androidx.compose.material:material-icons-core:${rootProject.extra["version_compose"]}")
    implementation("androidx.compose.material:material-icons-extended:${rootProject.extra["version_compose"]}")
    // Integration with activities
    implementation("androidx.activity:activity-compose:${rootProject.extra["version_compose_minor"]}")
    // Integration with observables
    implementation("androidx.compose.runtime:runtime-livedata:${rootProject.extra["version_compose"]}")
    implementation("androidx.compose.compiler:compiler:${rootProject.extra["version_compose"]}")

    //Accompanist permission
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
}