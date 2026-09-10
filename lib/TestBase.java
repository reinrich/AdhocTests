import java.lang.reflect.Method;

public class TestBase {

    private Method lookupMethod(String tm) {
        for (Method m : getClass().getDeclaredMethods()) {
            if (m.getName().equals(tm)) return m;
        }
        throw new RuntimeException("No method named " + tm);
    }

    // Map test method tm to args. Do it round robin if the length of args is
    // longer than rm's parameter list.
    // Repeat this very often to get tm jit compiled.
    // Compare first (interpreted results) with last (jit compiled) results.
    public void mapTestMethod(String tm, Object... args) throws Exception {
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
            boolean match = (expected[r] == result) || expected[r].equals(result);
            String statusMsg = match ? "OK" : "MISMATCH (expected=" + expected[r] + ")";
            System.out.println(tm + java.util.Arrays.toString(iargs) + " = " + result + " " + statusMsg);
            if (!match) {
                throw new RuntimeException("FAILURE: " + statusMsg);
            }
        }
    }

}
