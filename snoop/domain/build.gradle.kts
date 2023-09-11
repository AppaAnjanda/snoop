import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Hilt
    implementation(libs.hilt.javax)
    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.test)
}