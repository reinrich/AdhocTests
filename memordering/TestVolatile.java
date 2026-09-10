public class TestVolatile extends TestBase {

    public static          int intField;
    public static volatile int vIntField;

    public static void main(String[] args) throws Exception {
        new TestVolatile().runTests();
    }

    public void runTests() throws Exception {
        mapTestMethod("dontinline_store_int_dojit", 1);
        mapTestMethod("dontinline_store_int_load_int_dojit", 1);
        mapTestMethod("dontinline_store_volatile_int_load_volatile_int_dojit", 1);
    }

    public static void dontinline_store_int_dojit(int i) {
        vIntField = i;
    }

    public static int dontinline_store_int_load_int_dojit(int i) {
        vIntField = i;
        return intField;
    }

    public static int dontinline_store_volatile_int_load_volatile_int_dojit(int i) {
        vIntField = i;
        return vIntField;
    }
}
