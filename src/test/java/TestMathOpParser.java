import cn.elytra.gtnh.cutcorners.util.math.MathOp;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMathOpParser {

    @Test
    void testMathOp() {
        assertEquals(MathOp.noop(), MathOp.parse(""));
        assertEquals(MathOp.noop(), MathOp.parse("~"));
        // =
        assertEquals(MathOp.replace(new BigDecimal(42)), MathOp.parse("=42"));
        // +-
        assertEquals(MathOp.plus(new BigDecimal(50)), MathOp.parse("+50"));
        assertEquals(MathOp.subtract(new BigDecimal(50)), MathOp.parse("-50"));
        // *x
        assertEquals(MathOp.multiply(new BigDecimal("1.5")), MathOp.parse("*1.5"));
        assertEquals(MathOp.multiply(new BigDecimal("5.0")), MathOp.parse("x5.0"));

        // attributes
        assertEquals(MathOp.replace(new BigDecimal(42)).coerceAtMost(new BigDecimal(30)), MathOp.parse("=42{max=30}"));
    }

    @Test
    void testMathOpCalc() {
        // =
        assertEquals(67, MathOp.parse("=67").apply(114514));
        // +-
        assertEquals(42, MathOp.parse("+10").apply(32));
        assertEquals(0, MathOp.parse("-42").apply(42.0));
        // *
        assertEquals(30F, MathOp.parse("*1.5").apply(20.0F));
        // coerce
        assertEquals(100, MathOp.parse("*1000{max=100}").apply(1));
        assertEquals(100, MathOp.parse("*1000{min=100}").apply(-1));
    }

}
