import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;

public class TestVectorAdd {

    private static final VectorSpecies<Integer> I_SPECIES = IntVector.SPECIES_MAX;
    private static int LENGTH = 1024;

    private static int[] ia1;
    private static int[] ia2;
    private static int[] ia3;
    private static int[] ia4;
    private static int[] ia5;
    private static int[] ia6;
    private static int[] ia7;
    private static int[] ia8;

    private static int[] ir ;

    public static void main(String args[]) {
        System.out.println("### Running test");
        for (int i = 10_000; i > 0; i--) {
            dontinline_dojit_add_vectors(1, 2, 3, 4, 5, 6, 7, 8, 9);
        }
    }

    static volatile long vF;
    static long dontinline_dojit_add_vectors(long l1, long l2, long l3, long l4, long l5, long l6, long l7, long l8, long l9) {
        long v1 = vF, v2 = vF, v3 = vF, v4 = vF, v5 = vF, v6 = vF, v7 = vF, v8 = vF, v9 = vF;

        for (int i = 0; i < LENGTH; i += I_SPECIES.length()) {
            IntVector a1v = IntVector.fromArray(I_SPECIES, ia1, i);
            IntVector a2v = IntVector.fromArray(I_SPECIES, ia2, i);
            IntVector a3v = IntVector.fromArray(I_SPECIES, ia3, i);
            IntVector a4v = IntVector.fromArray(I_SPECIES, ia4, i);
            IntVector a5v = IntVector.fromArray(I_SPECIES, ia5, i);
            IntVector a6v = IntVector.fromArray(I_SPECIES, ia6, i);
            IntVector a7v = IntVector.fromArray(I_SPECIES, ia7, i);
            IntVector a8v = IntVector.fromArray(I_SPECIES, ia8, i);
            int scalar = dontinline_dojit_callForSpilling();
            a1v
                .add(a2v)
                .add(a3v)
                .add(a4v)
                .add(a5v)
                .add(a6v)
                .add(a7v)
                .add(a8v)
                .add(scalar).intoArray(ir, i);
        }
        return l1 + l2 + l3 + l4 + l5 + l6 + l7 + l8 + l9 +
            v1 + v2 + v3 + v4 + v5 + v6 + v7 + v8 + v9;
    }

    static int dontinline_dojit_callForSpilling() {
        return 42;
    }

    static {
        ia1 = new int[LENGTH];
        ia2 = new int[LENGTH];
        ia3 = new int[LENGTH];
        ia4 = new int[LENGTH];
        ia5 = new int[LENGTH];
        ia6 = new int[LENGTH];
        ia7 = new int[LENGTH];
        ia8 = new int[LENGTH];

        ir  = new int[LENGTH];
    }
}
