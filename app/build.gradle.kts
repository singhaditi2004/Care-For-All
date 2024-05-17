plugins {
    id("com.android.application")
    //id("com.google.gms.google-services") version "4.3.8"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.google_forms_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.google_forms_app"
        minSdk = 24
        targetSdk = 33
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

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
    }



    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.google.android.gms:play-services-auth:19.2.0")// For Google Sign-In
    implementation ("com.google.api-client:google-api-client:1.32.1")
    implementation ("com.google.apis:google-api-services-sheets:v4-rev581-1.25.0")
    // implementation ("com.google.apis:google-api-services-drive:v3-rev492-1.25.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
   // implementation ("com.google.firebase:firebase-auth:21.0.1")
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //  implementation ("com.google.firebase:firebase-core:21.0.1")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("com.google.firebase:firebase-auth:21.0.1")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.android.volley:volley:1.2.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.google.firebase:firebase-database:20.0.1")





}
