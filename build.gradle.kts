plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

buildscript {
    dependencies {
        // Aquí agregamos solo la dependencia para el plugin
        classpath("com.android.tools.build:gradle:8.0.0")
    }
}

allprojects {
    // Deja este bloque vacío, ya que los repositorios deben ir en el archivo de configuración global
}
