plugins {
    id("com.gtnewhorizons.gtnhconvention")
    id("com.github.ElytraServers.elytra-conventions") version "v1.1.2"
}

elytraModpackVersion {
    gtnhVersion = "2.9.0-beta-1"
    attachManifestVersionToJar = true
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    // JSpecify
    compileOnly("org.jspecify:jspecify:1.0.0")
    // JUnit
    testImplementation(platform("org.junit:junit-bom:5.14.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Mod dependencies

    devOnlyNonPublishable(nh.gt5Unofficial)
    devOnlyNonPublishable(nh.botania)
    devOnlyNonPublishable(nh.galaxySpaceGtnh)
    devOnlyNonPublishable(nh.railcraft)
    devOnlyNonPublishable(nh.tinkersConstruct)
    devOnlyNonPublishable(nh.newHorizonsCoreMod) {
        exclude(group = "net.gleast")
    }
    runtimeOnlyNonPublishable(nh.notEnoughItems)
    runtimeOnlyNonPublishable(nh.waila)
    runtimeOnlyNonPublishable(nh.enderCore)
    runtimeOnlyNonPublishable(nh.enderIo)
    runtimeOnlyNonPublishable(nh.mobsInfo)
    devOnlyNonPublishable("ganymedes01.etfuturum:Et-Futurum-Requiem:" + nh.versions.etFuturumRequiem.get())
    implementation("thaumcraft:Thaumcraft:1.7.10-4.2.3.5:dev") { isTransitive = false }

    devOnlyNonPublishable("com.github.ElytraServers:conditional-mixin-legacy:1.1.0")
}

tasks.test {
    useJUnitPlatform()
}
