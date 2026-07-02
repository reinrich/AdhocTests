import java.lang.reflect.Method;

public class TestL2I {

    public static void main(String[] args) throws Exception {
        mapTestMethod("dontinline_tc_addI_dojit", i2l(1), i2l(2));
        mapTestMethod("dontinline_tc_addI_2_dojit", i2l(1), i2l(2));
        mapTestMethod("dontinline_tc_addI_3_dojit", i2l(1), i2l(2));
        mapTestMethod("dontinline_tc_subI_dojit", i2l(1), i2l(2));
        mapTestMethod("dontinline_tc_subI_2_dojit", i2l(1), i2l(2));
        mapTestMethod("dontinline_tc_subI_3_dojit", i2l(1), 2);
        mapTestMethod("dontinline_tc_subI_4_dojit", i2l(1));
        mapTestMethod("dontinline_tc_cmoveL_dojit", i2l(1), i2l(2), i2l(3), i2l(4), i2l(1), i2l(2), i2l(4), i2l(3));
        mapTestMethod("dontinline_tc_cmoveI_dojit", i2l(1), i2l(2), 3, 4, i2l(1), i2l(2), 4, 3);
        mapTestMethod("dontinline_tc_negI_dojit", i2l(1));
        mapTestMethod("dontinline_tc_div_minus_1_dojit", i2l(1));
        mapTestMethod("dontinline_tc_div_2_dojit", i2l(27), i2l(3), i2l(27), i2l(0), i2l(27), i2l(1));
        mapTestMethod("dontinline_tc_udiv_dojit", i2l(27), i2l(3), i2l(27), 0L);
        mapTestMethod("dontinline_tc_absI_dojit", i2l(1));
        mapTestMethod("dontinline_tc_mulI_dojit", i2l(1), i2l(2));
        mapTestMethod("dontinline_tc_mulI_2_dojit", i2l(2));
        mapTestMethod("dontinline_tc_cmpI_imm_dojit", i2l(2), i2l(12345), i2l(12346));
        mapTestMethod("dontinline_tc_arsh_imm_dojit", i2l(12345));
        mapTestMethod("dontinline_tc_andI_dojit", i2l(1), i2l(2));
        mapTestMethod("dontinline_tc_orI_dojit", i2l(1), i2l(2));
        mapTestMethod("dontinline_tc_andcI_dojit", i2l(1), i2l(2));
        mapTestMethod("dontinline_tc_conv2B_dojit", i2l(0), i2l(2), i2l(3));
        mapTestMethod("dontinline_tc_convL2II2L_dojit", 0, 2, 3);
        mapTestMethod("dontinline_tc_convL2I_dojit", i2l(0), i2l(2), i2l(3));
        mapTestMethod("dontinline_tc_convI2L_dojit", i2l(0), i2l(2), i2l(3));
        mapTestMethod("dontinline_tc_popcountI_dojit", i2l(0), i2l(2), i2l(3));
        mapTestMethod("dontinline_tc_CountTrailingZerosI_dojit", i2l(0), i2l(2), i2l(3));
        mapTestMethod("dontinline_tc_ReverseBytesI_dojit", i2l(0), i2l(2), i2l(3));
        mapTestMethod("dontinline_tc_ReverseBytesUS_dojit", i2l(0), i2l(2), i2l(3));
    }

    static int dontinline_tc_absI_dojit(long a) {
        return Math.abs((int)a);
    }

    static int dontinline_tc_negI_dojit(long a) {
        return -(int)a;
    }

    static int dontinline_tc_div_minus_1_dojit(long a) {
        return (int)a/-1;
    }

    // l2i not elided; 0 check -> uc trap after l2i
    static int dontinline_tc_div_2_dojit(long a, long b) {
        if ((int)b == 0) {
            return 0;
        }
        return (int)a / (int)b;
    }

    static int dontinline_tc_udiv_dojit(long a, long b) {
        if ((int)b == 0) {
            return 0;
        }
        return Integer.divideUnsigned((int)a, (int)b);
    }

    static int dontinline_tc_cmoveL_dojit(long a, long b, long c, long d) {
        return ((int)c <= (int)d) ? (int)b : (int)a;
    }

    static int dontinline_tc_cmoveI_dojit(long a, long b, int c, int d) {
        return (c <= d) ? (int)b : (int)a;
    }

    // targeted towards addI_regL_regL but c2 uses addI_reg_reg instead
    static int dontinline_tc_addI_dojit(long a, long b) {
        return (int)(a + b);
    }

    static int dontinline_tc_addI_2_dojit(long a, long b) {
        return (int)a + (int)b;
    }

    // 2 uses prevent integration of ConvL2I into operations
    static int dontinline_tc_addI_3_dojit(long a, long b) {
        return (int)a + (int)b + (int)a;
    }

    static int dontinline_tc_andI_dojit(long a, long b) {
        return (int)a & (int)b;
    }

    static int dontinline_tc_orI_dojit(long a, long b) {
        return (int)a | (int)b;
    }

    static int dontinline_tc_subI_dojit(long a, long b) {
        return (int)(a - b);
    }

    static int dontinline_tc_subI_2_dojit(long a, long b) {
        return (int)a - (int)b;
    }

    static int dontinline_tc_subI_3_dojit(long a, int b) {
        return (int)a - b;
    }

    static int dontinline_tc_subI_4_dojit(long a) {
        return (int)a - 42;
    }

    static int dontinline_tc_mulI_dojit(long a, long b) {
        return (int)a * (int)b;
    }

    static int dontinline_tc_mulI_2_dojit(long a) {
        return (int)a * 12345;
    }

    static long dontinline_tc_cmpI_imm_dojit(long a) {
        if ((int)a < 12345) {
            return a;
        }
        return 0;
    }

    static int dontinline_tc_arsh_imm_dojit(long a) {
        return (int)a >> 5;
    }

    static int dontinline_tc_andcI_dojit(long a, long b) {
        return ((int)a ^ -1) & (int)b;
    }

    static int dontinline_tc_conv2B_dojit(long a) {
        return (int)a == 0 ? 0 : 1;
    }

    // instruct sxtI_reg(iRegIdst dst, iRegIsrc src)
    static int dontinline_tc_convL2II2L_dojit(int a) {
        return (int)(long)a;
    }

    static int dontinline_tc_convL2I_dojit(long a) {
        return (int)a;
    }

    static long dontinline_tc_convI2L_dojit(long a) {
        return (int)a;
    }

    static int dontinline_tc_popcountI_dojit(long a) {
        return Integer.bitCount((int)a);
    }

    static int dontinline_tc_CountTrailingZerosI_dojit(long a) {
        return Integer.numberOfTrailingZeros((int)a);
    }

    static int dontinline_tc_ReverseBytesI_dojit(long a) {
        return Integer.reverseBytes((int)a);
    }

    static int dontinline_tc_ReverseBytesUS_dojit(long a) {
        return Short.reverseBytes((short)a);
    }

    // -- helper methods --

    static long i2l(int a) {
        return (long)a | 0xffffffff00000000L; // set all bits in high word to provoke overflow
    }

    static Class<?> unbox(Class<?> c) {
        if (c == Integer.class) return int.class;
        if (c == Long.class)    return long.class;
        if (c == Double.class)  return double.class;
        if (c == Float.class)   return float.class;
        if (c == Boolean.class) return boolean.class;
        if (c == Byte.class)    return byte.class;
        if (c == Short.class)   return short.class;
        if (c == Character.class) return char.class;
        return c;
    }

    static Method lookupMethod(String tm) {
        for (Method m : TestL2I.class.getDeclaredMethods()) {
            if (m.getName().equals(tm)) return m;
        }
        throw new RuntimeException("No method named " + tm);
    }

    // Map test method tm to args. Do it round robin if the length of args is
    // longer than rm's parameter list.
    // Repeat this very often to get tm jit compiled.
    // Compare first (interpreted results) with last (jit compiled) results.
    static void mapTestMethod(String tm, Object... args) throws Exception {
        Method m = lookupMethod(tm);
        int arity = m.getParameterCount();
        if (args.length % arity != 0) {
            throw new IllegalArgumentException("args.length (" + args.length + ") must be a multiple of tm arity (" + arity + ")");
        }
        int rounds = args.length / arity;

        // collect expected results from first round (before warmup)
        Object[] expected = new Object[rounds];
        for (int r = 0; r < rounds; r++) {
            Object[] callArgs = java.util.Arrays.copyOfRange(args, r * arity, (r + 1) * arity);
            expected[r] = m.invoke(null, callArgs);
        }

        // warmup: call round-robin over all parameter sets
        for (int i = 0; i < 30000; i++) {
            int r = i % rounds;
            Object[] callArgs = java.util.Arrays.copyOfRange(args, r * arity, (r + 1) * arity);
            m.invoke(null, callArgs);
        }

        // verify results after warmup
        for (int r = 0; r < rounds; r++) {
            Object[] callArgs = java.util.Arrays.copyOfRange(args, r * arity, (r + 1) * arity);
            Object result = m.invoke(null, callArgs);
            Object[] iargs = java.util.Arrays.stream(callArgs).map(a -> ((Number)a).intValue()).toArray();
            boolean match = expected[r].equals(result);
            String statusMsg = match ? "OK" : "MISMATCH (expected=" + expected[r] + ")";
            System.out.println(tm + java.util.Arrays.toString(iargs) + " = " + result + " " + statusMsg);
            if (!match) {
                throw new RuntimeException("FAILURE: " + statusMsg);
            }
        }
    }
}
