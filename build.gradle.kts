// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("kotlin_version", "1.9.21")
        set("version_android_gradle_plugin", "8.10.1")
        set("room_version", "2.6.1")
        set("version_coroutines", "1.0.1")
        set("version_paging", "3.2.1")
        set("version_koin", "3.5.3")
        set("version_compose", "1.5.4")
        set("version_compose_minor", "1.3.0-alpha03")
        set("version_decompose", "2.2.3")
        set("version_decompose_ext", "3.0.0")
        set("version_decompose_lifecycle", "2.0.0")
        set("version_mockk", "1.10.6")
        set("version_junit", "4.13.2")
        set("version_coroutines_test", "1.4.3")
        set("version_core_testing", "2.1.0")
    }
}

plugins {
    id("com.android.application") version "8.10.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.21" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21" apply false
}