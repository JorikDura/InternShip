plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization")
    id("io.realm.kotlin")
}

android {
    namespace = "com.internship"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.internship"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://cars.cprogroup.ru/api/rubetek\"")
            buildConfigField("String", "API_CAMERAS", "\"/cameras/\"")
            buildConfigField("String", "API_DOORS", "\"/doors/\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"https://cars.cprogroup.ru/api/rubetek\"")
            buildConfigField("String", "API_CAMERAS", "\"/cameras/\"")
            buildConfigField("String", "API_DOORS", "\"/doors/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0-beta01")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //ui
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    //coil
    implementation("io.coil-kt:coil-compose:2.5.0")
    //foundation
    implementation("androidx.compose.foundation:foundation:1.6.0-beta03")
    //material
    implementation("androidx.compose.material:material:1.5.4")
    //for live data
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
    //ktor
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-android:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-client-logging:2.3.7")
    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    //realm
    implementation("io.realm.kotlin:library-base:1.11.0")
    implementation("io.realm.kotlin:library-sync:1.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}