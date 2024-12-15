import java.util.Properties

val keyProperties = Properties()
keyProperties.load(project.rootProject.file("local.properties").inputStream())

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.tpap"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.tpap"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "GPT_API_KEY", "\"${keyProperties["GPT_API_KEY"]}\"")
        buildConfigField("String", "MAP_API_KEY", "\"${keyProperties["MAP_API_KEY"]}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.places)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.retrofit)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}