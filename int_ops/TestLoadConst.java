public class TestLoadConst extends TestBase {

    public static void main(String[] args) throws Exception {
        new TestLoadConst().runTests();
    }

    public void runTests() throws Exception {
        mapTestMethod("dontinline_dojit_field16_1");
        mapTestMethod("dontinline_dojit_field16_1b");
        mapTestMethod("dontinline_dojit_field16_2");
        mapTestMethod("dontinline_dojit_field16_3");
        mapTestMethod("dontinline_dojit_37_consec_ones");
        mapTestMethod("dontinline_dojit_37_consec_ones_wrapped");
        mapTestMethod("dontinline_dojit_20_consec_ones_simm32");
        mapTestMethod("dontinline_dojit_20_nonconsec_ones_simm32");
    }

    static long dontinline_dojit_37_consec_ones() {
        return ((1L << 37) - 1) << 3;
    }

    static long dontinline_dojit_37_consec_ones_wrapped() {
        return ~(((1L << 37) - 1) << 3);
    }

    // pli with -XX:PowerArchitecturePPC64=10
    static long dontinline_dojit_20_consec_ones_simm32() {
        return ((1L << 20) - 1) << 3;
    }

    // lis; ori;
    static long dontinline_dojit_20_nonconsec_ones_simm32() {
        return (((1L << 20) - 1) << 3) | 1;
    }

    // single lis
    static long dontinline_dojit_field16_1() {
        return 0x0000_0000_abcd_0000;
    }

    static long dontinline_dojit_field16_1b() {
        return 0x0000_0000_abcd_0000L
             | 1L << 31; // ! simm32
    }

    static long dontinline_dojit_field16_2() {
        return 0x0000_0000_00ab_cd00L;
    }

    static long dontinline_dojit_field16_3() {
        return 0x0000_abcd_0000_0000L;
    }
}
