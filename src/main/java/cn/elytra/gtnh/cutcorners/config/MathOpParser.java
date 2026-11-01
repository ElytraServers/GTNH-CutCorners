package cn.elytra.gtnh.cutcorners.config;

import cn.elytra.gtnh.cutcorners.util.fp.MayThrow;
import cn.elytra.gtnh.cutcorners.util.math.MathOp;
import org.jspecify.annotations.NullMarked;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("SameParameterValue")
@NullMarked
public class MathOpParser {

    public static MayThrow<MathOp> parse(String s) {
        return MayThrow.capture(() -> parseOrThrow(s));
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
            op = op.coerceAtLeast(new BigDecimal(attrs.get("min")));
        }
        if (attrs.containsKey("max")) {
            op = op.coerceAtMost(new BigDecimal(attrs.get("max")));
        }
        return op;
    }

    private static BigDecimal bigDecimal(String s) {
        try {
            return new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid BigDecimal: " + s, e);
        }
    }

}
