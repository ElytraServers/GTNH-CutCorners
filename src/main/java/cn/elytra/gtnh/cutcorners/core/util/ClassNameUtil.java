package cn.elytra.gtnh.cutcorners.core.util;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@ApiStatus.Internal
@NullMarked
public class ClassNameUtil {

    public static boolean isClassInPackage(String canonicalName, String packageName) {
        if (!packageName.endsWith(".")) return isClassInPackage(canonicalName, packageName + ".");
        return canonicalName.startsWith(packageName);
    }

    /// Split the canonical class name into the common package name and the detailed class name.
    ///
    /// It's guaranteed that the returned Array contains exactly 2 non-null String elements.
    ///
    /// For example, given `com.example.foo.bar.Baz`, the common package is `com.example.foo`,
    /// it will result in `["com.example.foo", "bar.Baz"]`.
    public static String[] getClassAsPackageAndDetailedName(String canonicalName, @Nullable String commonPackageName) {
        if (commonPackageName == null || commonPackageName.isEmpty()) {
            int i = canonicalName.lastIndexOf('.');
            return i != -1
                ? new String[] { canonicalName.substring(0, i + 1 /* include the dot */),
                    canonicalName.substring(i + 1) }
                : new String[] { "", canonicalName };
        }
        if (!commonPackageName.endsWith(".")) {
            // a common package should be ended in '.'.
            return getClassAsPackageAndDetailedName(canonicalName, commonPackageName + ".");
        }

        if (canonicalName.startsWith(commonPackageName)) {
            String detailedClassName = canonicalName.substring(commonPackageName.length());
            return new String[] { commonPackageName, detailedClassName };
        } else {
            // the class is not in the common package.
            // we don't throw, but treat everything as the detailed class name.
            return new String[] { "", canonicalName };
        }
    }

    public static String getClassNameWithShortenedPackage(String canonicalName) {
        String[] ss = getClassAsPackageAndDetailedName(canonicalName, null);
        ss[0] = getShortenPackageName(ss[0]);
        return ss[0] + ss[1];
    }

    public static String getClassNameWithShortenedCommonPackage(String canonicalName, String commonPackageName) {
        String[] ss = getClassAsPackageAndDetailedName(canonicalName, commonPackageName);
        ss[0] = getShortenPackageName(ss[0]);
        return ss[0] + ss[1];
    }

    public static String getShortenPackageName(@Nullable String packageName) {
        return getShortenPackageName(packageName, 1);
    }

    /// Shorten the strings between dots.
    ///
    /// For example, given `aaa.bbb.ccc` with preserve length of 1, results in `a.b.c`;
    /// or preserve length of 2, results in `aa.bb.cc`.
    public static String getShortenPackageName(@Nullable String packageName, int preserveLength) {
        if (preserveLength <= 0) throw new IllegalArgumentException("preserve length must not be zero or negative");
        if (packageName == null || packageName.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        int start = 0;
        while (start < packageName.length()) {
            int end = packageName.indexOf('.', start);
            if (end == -1) {
                end = packageName.length();
                sb.append(packageName, start, Math.min(end, start + preserveLength));
            } else {
                sb.append(packageName, start, Math.min(end, start + preserveLength)).append('.');
            }
            start = end + 1;
        }
        return sb.toString();
    }
}
