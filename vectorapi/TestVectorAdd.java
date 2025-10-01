import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;

public class TestVectorAdd {

    private static final VectorSpecies<Integer> I_SPECIES = IntVector.SPECIES_MAX;
    private static int LENGTH = 1024;

    private static int[] ia;
    private static int[] ib;
    private static int[] ir;

    public static void main(String args[]) {
        System.out.println("### Running test");
        for (int i = 10_000; i > 0; i--) {
            dontinline_dojit_add_vectors(1, 2, 3, 4, 5, 6, 7, 8, 9);
        }
    }

    static long dontinline_dojit_add_vectors(long l1, long l2, long l3, long l4, long l5, long l6, long l7, long l8, long l9) {
        for (int i = 0; i < LENGTH; i += I_SPECIES.length()) {
            IntVector av = IntVector.fromArray(I_SPECIES, ia, i);
            IntVector bv = IntVector.fromArray(I_SPECIES, ib, i);
            int scalar = dontinline_dojit_callForSpilling();
            av.add(bv).add(scalar).intoArray(ir, i);
        }
        return l1 +  l2 +  l3 +  l4 +  l5 +  l6 +  l7 +  l8 + l9;
    }

    static int dontinline_dojit_callForSpilling() {
        return 42;
    }

    static {
        ia = new int[LENGTH];
        ib = new int[LENGTH];
        ir = new int[LENGTH];
    }
}
