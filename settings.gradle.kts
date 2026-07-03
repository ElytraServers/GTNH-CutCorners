pluginManagement {
    repositories {
        maven {
            // RetroFuturaGradle
            name = "GTNH Maven"
            url = uri("https://nexus.gtnewhorizons.com/repository/public/")
            mavenContent {
                includeGroup("com.gtnewhorizons")
                includeGroupByRegex("com\\.gtnewhorizons\\..+")
            }
        }
        maven {
            name = "JitPack"
            url = uri("https://jitpack.io")
            mavenContent {
                includeGroupByRegex("com\\.github\\..+")
            }
        }
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("com.gtnewhorizons.gtnhsettingsconvention") version "2.0.26"
    id("cn.elytra.gradle.conventions.settings") version "1.2.0-beta.1"
}

elytra {
    versionCatalogs {
        create("nh") {
            version = "2.9.0-beta-1"
        }
    }
}
