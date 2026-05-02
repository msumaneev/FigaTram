plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

import java.util.Properties
import java.io.FileInputStream
import java.io.FileWriter

val versionPropsFile = file("../version.properties")
val versionProps = Properties()
if (versionPropsFile.canRead()) {
    versionProps.load(FileInputStream(versionPropsFile))
}

val vCode = versionProps["VERSION_CODE"]?.toString()?.toInt() ?: 1
val vName = versionProps["VERSION_NAME"]?.toString() ?: "1.0.0"

android {
    namespace = "com.figatram"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.figatram"
        minSdk = 26
        targetSdk = 34
        versionCode = vCode
        versionName = vName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // ViewModel and Coroutines
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Google Maps
    implementation("com.google.maps.android:maps-compose:4.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // Retrofit2 & OkHttp3
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")

    // WorkManager
    val workVersion = "2.9.0"
    implementation("androidx.work:work-runtime-ktx:$workVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

tasks.register("bumpVersion") {
    doLast {
        val propsFile = file("../version.properties")
        if (propsFile.canRead()) {
            val props = Properties()
            props.load(FileInputStream(propsFile))
            val currentCode = props["VERSION_CODE"]?.toString()?.toInt() ?: 1
            val currentName = props["VERSION_NAME"]?.toString() ?: "1.0.0"
            
            // Increment version code
            val newCode = currentCode + 1
            
            // Increment patch version in versionName (e.g., 1.0.0 -> 1.0.1)
            val parts = currentName.split(".")
            val newName = if (parts.size == 3) {
                "${parts[0]}.${parts[1]}.${(parts[2].toInt() + 1)}"
            } else {
                currentName
            }
            
            props["VERSION_CODE"] = newCode.toString()
            props["VERSION_NAME"] = newName
            
            propsFile.outputStream().use { os ->
                props.store(os, null)
            }
            println("Bumped version to $newName ($newCode)")
        }
    }
}

// Automatically bump version before assembleDebug or installDebug
tasks.whenTaskAdded {
    if (name == "assembleDebug" || name == "installDebug") {
        dependsOn("bumpVersion")
    }
}
