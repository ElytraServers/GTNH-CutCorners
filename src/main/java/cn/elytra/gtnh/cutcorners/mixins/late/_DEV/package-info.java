/// Mixin classes in _DEV package should be only loaded in development environment, or deobfuscated environment.
/// Use `@Condition(type = Condition.Type.TESTER, tester = DevelopmentEnvironmentTester.class)` to disallow loading in product environment.
package cn.elytra.gtnh.cutcorners.mixins.late._DEV;
