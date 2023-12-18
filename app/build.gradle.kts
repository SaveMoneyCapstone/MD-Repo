plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.dicoding.savemoney"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.savemoney"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        buildConfigField("String", "API_KEY", "\"79af5fa9-d33f-518c-4e6f-ed21bfaa\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("com.google.firebase:firebase-firestore:24.10.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    val nav_version = "2.5.3"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //gms signup account google
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation ("com.github.bumptech.glide:glide:4.13.2")
    //preference
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //card
    implementation("androidx.cardview:cardview:1.0.0")

    //bar
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //preference
    val preference_version = "1.2.0"
    implementation("androidx.preference:preference-ktx:$preference_version")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //coil
    implementation("io.coil-kt:coil:2.5.0")

    //room
    val room_version = "2.5.0"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation ("androidx.room:room-ktx:2.5.1")

    implementation("androidx.work:work-runtime:2.8.1")
    implementation("com.loopj.android:android-async-http:1.4.10")
}