plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.controlefinanceiro"
    compileSdk = 30  // Versão do SDK Android

    defaultConfig {
        applicationId = "com.example.expensetracker"  // ID do aplicativo
        minSdk = 26  // Versão mínima do SDK Android
        targetSdk = 31  // Versão de destino do SDK Android
        versionCode = 1
        versionName = "1.0"
    }
    
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    
    
}
    
    dependencies {
        implementation("androidx.appcompat:appcompat:1.3.1")  // Biblioteca de compatibilidade
        implementation("com.google.firebase:firebase-database:20.0.3")  // Biblioteca Firebase
        implementation("androidx.constraintlayout:constraintlayout:2.0.4")  // Layout de restrição
        implementation("com.google.gms:google-services:4.3.10")  // Para configurar o Firebase
    }
    
