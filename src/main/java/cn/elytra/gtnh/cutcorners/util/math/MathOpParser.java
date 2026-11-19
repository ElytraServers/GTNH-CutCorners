package cn.elytra.gtnh.cutcorners.util.math;

import com.google.common.collect.Maps;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("SameParameterValue")
@NullMarked
class MathOpParser {

    // case-insensitive tree
    private static final Map<String, BigDecimal> MIN_MAX_LOOKUP;

    static {
        MIN_MAX_LOOKUP = Maps.newTreeMap(String.CASE_INSENSITIVE_ORDER);
        // int
        MIN_MAX_LOOKUP.put("int_min", new BigDecimal(Integer.MIN_VALUE));
        MIN_MAX_LOOKUP.put("int_max", new BigDecimal(Integer.MAX_VALUE));
        // long
        MIN_MAX_LOOKUP.put("long_min", new BigDecimal(Long.MIN_VALUE));
        MIN_MAX_LOOKUP.put("long_max", new BigDecimal(Long.MAX_VALUE));
        // float
        MIN_MAX_LOOKUP.put("float_min", new BigDecimal(Float.MIN_VALUE));
        MIN_MAX_LOOKUP.put("float_max", new BigDecimal(Float.MAX_VALUE));
        // double
        MIN_MAX_LOOKUP.put("double_min", new BigDecimal(Double.MIN_VALUE));
        MIN_MAX_LOOKUP.put("double_max", new BigDecimal(Double.MAX_VALUE));
    }

    public static MathOp parseOrThrow(String input) {
        String s = input.trim();
        if (s.isEmpty()) return MathOp.noop();

        MathOp op;
        Map<String, String> attr;
        char ch = s.charAt(0);
        switch (ch) {
            case '~' -> {
                return MathOp.noop();
            }
            case '=' -> {
                int numEnd = endIndexOfNum(s, 1);
                BigDecimal numVal = bigDecimal(s.substring(1, numEnd));
                op = MathOp.replace(numVal);
                attr = parseAttributes(s, numEnd);
            }
            case '+', '-' -> {
                boolean sig = ch == '+';
                int numEnd = endIndexOfNum(s, 1);
                BigDecimal numVal = bigDecimal(s.substring(1, numEnd));
                op = sig ? MathOp.plus(numVal) : MathOp.subtract(numVal);
                attr = parseAttributes(s, numEnd);
            }
            case 'x', '*' -> {
                int numEnd = endIndexOfNum(s, 1);
                BigDecimal numVal = bigDecimal(s.substring(1, numEnd));
                op = MathOp.multiply(numVal);
                attr = parseAttributes(s, numEnd);
            }
            default ->
                throw new IllegalArgumentException("invalid leading character: \"" + ch + "\", not supported mode.");
        }

        op = applyAttributes(op, attr);
        return op;
    }

    private static int endIndexOfNum(String s, int start) {
        int end = start;
        while (end < s.length()) {
            char ch = s.charAt(end);
            if (!Character.isDigit(ch) && ch != '.') break;
            end++;
        }
        return end;
    }

    private static Map<String, String> parseAttributes(String s, int start) {
        if (start >= s.length() || s.charAt(start) != '{') {
            return Collections.emptyMap();
        }
        int end = s.lastIndexOf('}');
        if (end == -1) throw new IllegalArgumentException("Attributes doesn't have an end (\"}\").");
        Map<String, String> attr = new LinkedHashMap<>();
        String s1 = s.substring(start + 1, end); // attributes without starting and closing brackets.
        for (String entry : s1.split(",")) {
            String[] ss = entry.split("=", 2);
            if (ss.length != 2) throw new IllegalArgumentException("Invalid attribute entry: " + entry);
            attr.put(ss[0].trim(), ss[1].trim());
        }
        return attr;
    }

    private static MathOp applyAttributes(MathOp op, Map<String, String> attrs) {
        if (attrs.containsKey("min")) {
            op = op.coerceAtLeast(bigDecimalExtended(attrs.get("min")));
        }
        if (attrs.containsKey("max")) {
            op = op.coerceAtMost(bigDecimalExtended(attrs.get("max")));
        }
        return op;
    }

    private static @Nullable BigDecimal lookupMinMax(String s) {
        return MIN_MAX_LOOKUP.get(s);
    }

    private static BigDecimal bigDecimalExtended(String s) {
        BigDecimal value = lookupMinMax(s);
        if (value != null) return value;

        return bigDecimal(s);
    }

    private static BigDecimal bigDecimal(String s) {
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid BigDecimal: " + s, e);
        }
    }

}
