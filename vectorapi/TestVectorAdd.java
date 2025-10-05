import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;

public class TestVectorAdd {

    private static final VectorSpecies<Integer> I_SPECIES = IntVector.SPECIES_128;
    private static int LENGTH = 1024;

    private static int[] ia1;
    private static int[] ia2;
    private static int[] ir ;

    public static void main(String args[]) {
        System.out.println("### Running test");
        for (int i = 10_000; i > 0; i--) {
            dontinline_dojit_add_vectors(1, 2, 3, 4, 5, 6, 7, 8, 9);
        }
    }

    static class LData {
        long l1, l2, l3, l4, l5, l6, l7, l8;
        LData() {
            l1 = vF; l2 = vF; l3 = vF; l4 = vF; l5 = vF; l6 = vF; l7 = vF; l8 = vF;
        }
        long sum() {
            return l1 + l2 + l3 + l4 + l5 + l6 + l7 + l8;
        }
    }

    public static volatile long vF;
    static long dontinline_dojit_add_vectors(long l1, long l2, long l3, long l4,
                                             long l5, long l6, long l7, long l8,
                                             long l9 /* odd stack arg */) {
        // To be scalar replaced and spilled to stack
        LData d1 = new LData();
        LData d2 = new LData();
        LData d3 = new LData();

        for (int i = 0; i < LENGTH; i += I_SPECIES.length()) {
            IntVector a1v = IntVector.fromArray(I_SPECIES, ia1, i);
            IntVector a2v = IntVector.fromArray(I_SPECIES, ia2, i);
            int scalar = dontinline_dojit_callForSpilling();
            a1v.add(a2v)
               .add(scalar).intoArray(ir, i);
        }

        return l1 + l2 + l3 + l4 + l5 + l6 + l7 + l8 + l9 +
               + d1.sum() + d2.sum() + d3.sum();
    }

    static int dontinline_dojit_callForSpilling() {
        return 42;
    }

    static {
        ia1 = new int[LENGTH];
        ia2 = new int[LENGTH];
        ir  = new int[LENGTH];
    }
}
