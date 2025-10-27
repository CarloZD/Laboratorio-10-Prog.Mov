plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.semana09_servicios"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.semana09_servicios"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose BOM (actualizado)
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Activity Compose (actualizado)
    implementation("androidx.activity:activity-compose:1.9.3")

    // ViewModel Compose (actualizado)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    // Retrofit - CON EXCLUSIONES PARA EVITAR DUPLICADOS
    implementation("com.squareup.retrofit2:retrofit:2.9.0") {
        exclude(group = "xpp3", module = "xpp3")
        exclude(group = "xmlpull", module = "xmlpull")
    }
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") {
        exclude(group = "xpp3", module = "xpp3")
        exclude(group = "xmlpull", module = "xmlpull")
    }

    // Coroutines (actualizado)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // Core (actualizado)
    implementation("androidx.core:core-ktx:1.15.0")

    // Navigation (probablemente NO lo necesites, pero lo dejo comentado)
    // implementation(libs.androidx.navigation.safe.args.generator)

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}