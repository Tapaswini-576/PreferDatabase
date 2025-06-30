plugins {
    id("com.android.application")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.googleServices) // KEEP THIS ONE
    id("kotlin-kapt")
    // REMOVE THIS LINE: id("com.google.gms.google-services") version "4.4.3" apply false
}


android {
    namespace = "com.example.preferdatabase"
    compileSdk = 35 // Current compileSdk

    defaultConfig {
        applicationId = "com.example.preferdatabase" // This must match your Firebase project's package name
        minSdk = 24
        targetSdk = 35 // Current targetSdk
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")

    //Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version") // Ensure you have the 'kotlin-kapt' plugin applied for this

    //firebase
    // Make sure your firebase-bom version is up-to-date.
    // As of my last update, 33.16.0 is relatively recent. You can check the latest on Firebase docs.
    implementation(platform("com.google.firebase:firebase-bom:33.16.0"))
    // Auth (Firebase recommended using -ktx for Kotlin projects)
    implementation("com.google.firebase:firebase-auth-ktx")
    // Analytics (You had this without -ktx, which is fine, it usually doesn't have a -ktx variant)
    implementation("com.google.firebase:firebase-analytics")
    // If you plan to use Firestore, you'd add:
    // implementation("com.google.firebase:firebase-firestore-ktx")
}