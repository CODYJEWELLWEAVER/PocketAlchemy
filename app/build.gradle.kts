plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

android {
    namespace = "com.android.pocketalchemy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.pocketalchemy"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-alpha"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            isDefault = true
            buildConfigField("Boolean", "DEBUG", "true")
        }

        release {
            isMinifyEnabled = false
            isDebuggable = false
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
    buildToolsVersion = "34.0.0"
}

dependencies {
    val dagger_version = "2.49"
    val lifecycle_version = "2.6.2"
    val navigation_version = "2.7.6"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")
    implementation("androidx.navigation:navigation-compose:$navigation_version")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.dagger:dagger:$dagger_version")
    implementation("com.google.dagger:hilt-android:$dagger_version")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
    ksp("com.google.dagger:dagger-compiler:$dagger_version")
    ksp("com.google.dagger:hilt-compiler:$dagger_version")
    ksp("androidx.hilt:hilt-compiler:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}