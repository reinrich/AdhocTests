public class TestLoadConst extends TestBase {

    public static void main(String[] args) throws Exception {
        new TestLoadConst().runTests();
    }

    public void runTests() throws Exception {
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
}
