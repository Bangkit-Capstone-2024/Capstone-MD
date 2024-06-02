plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.cpstn.momee"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cpstn.momee"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.dev.momee.id/api/v1/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidxActivityKttx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.coil)

    // network
    implementation(libs.retrofit)
    implementation(libs.retrofitGsonConverter)
    implementation(libs.gson)
    implementation(libs.chucker)

    // android architecture component
    implementation(libs.viewModel)
    implementation(libs.viewModelLifeCycle)
    implementation(libs.liveData)
    implementation(libs.fragmentKtx)

    // coroutine
    implementation(libs.coroutinesCore)
    implementation(libs.coroutinesAndroid)

    // hilt
    implementation(libs.hiltAndroid)
    kapt(libs.hiltCompiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}