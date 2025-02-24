plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.savor"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.savor"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    // For control over item selection of both touch and mouse driven selection
    implementation("androidx.recyclerview:recyclerview-selection:1.1.0")
    //Glide
    implementation(libs.glide)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.11.0")

    //lotti
    implementation(libs.lottie);
    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.firebase.auth)
    //Room
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
    implementation ("androidx.room:room-rxjava3:2.6.1")
    //youtube
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.1")
    // RxJava 3
    implementation ("io.reactivex.rxjava3:rxjava:3.1.6")
    // RxAndroid  (UI thread)
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
}