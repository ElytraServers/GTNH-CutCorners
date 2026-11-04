plugins {
    id("com.gtnewhorizons.gtnhconvention")
    id("com.github.ElytraServers.elytra-conventions") version "v1.1.2"
}

elytraModpackVersion {
    gtnhVersion = "2.9.0"
    attachManifestVersionToJar = true
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    fun ModuleDependency.dev(transitive: Boolean = true) {
        artifact { classifier = "dev" }
        if (!transitive) isTransitive = false
    }

    // JSpecify
    compileOnly("org.jspecify:jspecify:1.0.0")
    // JUnit
    testImplementation(platform("org.junit:junit-bom:5.14.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Mod dependencies

    devOnlyNonPublishable(nh.gt5Unofficial) { dev() }
    devOnlyNonPublishable(nh.galaxySpaceGtnh) { dev() }

    devOnlyNonPublishable(nh.botania) { dev(false) }
    devOnlyNonPublishable(nh.railcraft) { dev(false) }
    devOnlyNonPublishable(nh.tinkersConstruct) { dev() }
    devOnlyNonPublishable(nh.newHorizonsCoreMod) { dev() }
    devOnlyNonPublishable(nh.cropsNh) { dev() }
    runtimeOnlyNonPublishable(nh.notEnoughItems) { dev() }
    runtimeOnlyNonPublishable(nh.waila) { dev() }
    runtimeOnlyNonPublishable(nh.enderCore) { dev() }
    runtimeOnlyNonPublishable(nh.enderIo) { dev() }
    runtimeOnlyNonPublishable(nh.appleCore) { dev() }
    runtimeOnlyNonPublishable(nh.betterBuildersWands) { dev() }
    runtimeOnlyNonPublishable(nh.forestryMc) { dev() }
    devOnlyNonPublishable("ganymedes01.etfuturum:Et-Futurum-Requiem:" + nh.versions.etFuturumRequiem.get()) {
        artifact { classifier = "dev" }
    }
    implementation("thaumcraft:Thaumcraft:1.7.10-4.2.3.5:dev") { isTransitive = false }

    devOnlyNonPublishable("com.github.ElytraServers:conditional-mixin-legacy:1.1.0") {
        artifact { classifier = "dev" }
    }
}

tasks.test {
    useJUnitPlatform()
}
