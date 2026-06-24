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

    fun gtnhVer(n: String) = project.elytraModpackVersion[n]

    fun gtnhDev(n: String) = project.elytraModpackVersion.gtnhdev(n)

    runtimeOnlyNonPublishable(gtnhDev("NotEnoughItems"))

    devOnlyNonPublishable(gtnhDev("GT5-Unofficial"))
    devOnlyNonPublishable(gtnhDev("Botania"))
    devOnlyNonPublishable(gtnhDev("Galaxy-Space-GTNH"))
    devOnlyNonPublishable(gtnhDev("Railcraft"))
    devOnlyNonPublishable(gtnhDev("TinkersConstruct"))
    devOnlyNonPublishable(gtnhDev("NewHorizonsCoreMod")) {
        exclude(group = "net.glease")
    }

    runtimeOnlyNonPublishable(gtnhDev("waila"))
    runtimeOnlyNonPublishable(gtnhDev("EnderCore"))
    runtimeOnlyNonPublishable(gtnhDev("EnderIO"))
    runtimeOnlyNonPublishable(gtnhDev("Mobs-Info"))

    devOnlyNonPublishable("ganymedes01.etfuturum:Et-Futurum-Requiem:" + gtnhVer("Et-Futurum-Requiem"))

    implementation("thaumcraft:Thaumcraft:1.7.10-4.2.3.5:dev") { isTransitive = false }

    devOnlyNonPublishable("com.github.ElytraServers:conditional-mixin-legacy:1.1.0")
}

tasks.test {
    useJUnitPlatform()
}
