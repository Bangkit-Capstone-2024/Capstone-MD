plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    namespace = "com.cpstn.momee"
    compileSdk = 34

    packaging {
        resources {
            excludes += "META-INF/DEPENDENCIES"
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = "key0"
            keyPassword = "momee1234"
            storeFile = file("/Users/syahrulfahmi/StudioProjects/Capstone-MD/app/src/momee")
            storePassword = "momee1234"
        }
    }


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
            signingConfig = signingConfigs.getByName("release")
            buildConfigField("String", "BASE_URL", "\"https://api.momee.id/api/v1/\"")
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
        mlModelBinding = true
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
    implementation(libs.uCrop)
    implementation(libs.lottie)
    implementation(libs.androidx.core)

    // navigation component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // network
    implementation(libs.retrofit)
    implementation(libs.retrofitGsonConverter)
    implementation(libs.gson)
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chuckerRelease)

    // android architecture component
    implementation(libs.viewModel)
    implementation(libs.viewModelLifeCycle)
    implementation(libs.liveData)
    implementation(libs.fragmentKtx)

    // coroutine
    implementation(libs.coroutinesCore)
    implementation(libs.coroutinesAndroid)

    // datastore
    implementation(libs.dataStore)

    // hilt
    implementation(libs.hiltAndroid)
    kapt(libs.hiltCompiler)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.firebase.messaging)
    implementation(libs.google.auth.library.oauth2.http)
    implementation(libs.play.services.location)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // shimmer
    implementation(libs.shimmer)

    // tensorflow
    implementation(libs.tensorflow.lite.metadata)
    implementation(libs.tensorflow.lite.task.vision)

    implementation(libs.viewpagerindicator)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}