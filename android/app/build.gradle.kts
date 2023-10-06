import org.jetbrains.kotlin.konan.properties.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

val localProperties = Properties()
localProperties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.appa.snoop"
    compileSdk = 34

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.appa.snoop"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "API_KEY", "\"" + localProperties["api_key"] + "\"")
        buildConfigField("String", "BASE_URL", "\"" + localProperties["base_url"] + "\"")

        addManifestPlaceholders(mutableMapOf("API_KEY" to localProperties["api_key"]!!))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "API_KEY", "\"" + localProperties["api_key"] + "\"")
            buildConfigField("String", "BASE_URL", "\"" + localProperties["base_url"] + "\"")

            addManifestPlaceholders(mutableMapOf("API_KEY" to localProperties["api_key"]!!))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext.junit)
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // retrofit && okhttp
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)

    //kakao
    implementation(libs.bundles.kakao)
}