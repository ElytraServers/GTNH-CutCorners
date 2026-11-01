import cn.elytra.gtnh.cutcorners.config.MathOpParser;
import cn.elytra.gtnh.cutcorners.util.math.MathOp;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMathOpParser {

    @Test
    void testMathOp() {
        assertEquals(MathOp.noop(), MathOpParser.parseOrThrow(""));
        assertEquals(MathOp.noop(), MathOpParser.parseOrThrow("~"));
        // =
        assertEquals(MathOp.replace(new BigDecimal(42)), MathOpParser.parseOrThrow("=42"));
        // +-
        assertEquals(MathOp.plus(new BigDecimal(50)), MathOpParser.parseOrThrow("+50"));
        assertEquals(MathOp.subtract(new BigDecimal(50)), MathOpParser.parseOrThrow("-50"));
        // *x
        assertEquals(MathOp.multiply(new BigDecimal("1.5")), MathOpParser.parseOrThrow("*1.5"));
        assertEquals(MathOp.multiply(new BigDecimal("5.0")), MathOpParser.parseOrThrow("x5.0"));

        // attributes
        assertEquals(MathOp.replace(new BigDecimal(42)).coerceAtMost(new BigDecimal(30)), MathOpParser.parseOrThrow("=42{max=30}"));
    }

    @Test
    void testMathOpCalc() {
        // =
        assertEquals(67, MathOpParser.parseOrThrow("=67").apply(114514));
        // +-
        assertEquals(42, MathOpParser.parseOrThrow("+10").apply(32));
        assertEquals(0, MathOpParser.parseOrThrow("-42").apply(42.0));
        // *
        assertEquals(30F, MathOpParser.parseOrThrow("*1.5").apply(20.0F));
        // coerce
        assertEquals(100, MathOpParser.parseOrThrow("*1000{max=100}").apply(1));
        assertEquals(100, MathOpParser.parseOrThrow("*1000{min=100}").apply(-1));
    }

}
