// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false

        // ...

        // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false


}
buildscript {
    repositories {
        google()  // já deve estar presente
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.15")  // Versão mais recente do plugin
    }
}

