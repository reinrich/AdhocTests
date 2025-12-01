import java.io.PrintStream;

public class HumongousGc2 {
    static final long MAX_JAVA_HEAP_MB = Runtime.getRuntime().maxMemory() >> 20;;
    static final long SMALL_OBJECT_SIZE_KB = 100;
    static final long SMALL_OBJECTS_PERCENTAGE = 10;
    static final long SMALL_OBJECTS_NUM = (MAX_JAVA_HEAP_MB * 1024 / SMALL_OBJECT_SIZE_KB) * SMALL_OBJECTS_PERCENTAGE / 100;
    static final byte[][] SMALL_OBJECTS = new byte[(int) SMALL_OBJECTS_NUM][];

    static final PrintStream OUT = System.out;
    private static final int HUM_LIST_LENGTH = 8;

    private static int smallObjectsAllocationRounds;

    public static void main(String[] args) throws Exception {

        if (args.length > 1) {
            smallObjectsAllocationRounds = Integer.parseInt(args[0]);
        } else {
            smallObjectsAllocationRounds = 50;
        }

        OUT.println("MAX_JAVA_HEAP_MB: " + MAX_JAVA_HEAP_MB);
        OUT.println("SMALL_OBJECTS_PERCENTAGE: " + SMALL_OBJECTS_PERCENTAGE + "%");
        OUT.println("SMALL_OBJECTS_NUM: " + SMALL_OBJECTS_NUM);
        OUT.println("Small objects heap usage / MB: " + (SMALL_OBJECTS_NUM * SMALL_OBJECT_SIZE_KB) / 1024);
        OUT.println("smallObjectsAllocationRoundss: " + smallObjectsAllocationRounds);

        allocSmallObjects();

        OUT.println("Done with small objects allocation.");
        Thread.sleep(500);
        OUT.println("-----------------------------------");
        OUT.println("Allocating humongous objects exclusively");
        OUT.println("-----------------------------------");
        Object[] humList = null;
        Object[] lastElem = null;
        for (int i = 1; i < 100; i++) {
            Object[] newElem = new Object[10_000_000];
            if (humList == null) {
                lastElem = newElem;
            }
            newElem[0] = humList;
            if ((i % HUM_LIST_LENGTH) == 0) {
                lastElem[0] = humList;
                humList = null;
            } else {
                humList = newElem;
            }
            System.out.println(i);
            Thread.sleep(100);
        }
    }

    private static void allocSmallObjects() throws Exception {
        OUT.println("-----------------------------------");
        OUT.println("Allocating small objects...");
        for (int j = 0; j < smallObjectsAllocationRounds; j++) {
            for (int i = 0; i < SMALL_OBJECTS_NUM; i += 2) {
                SMALL_OBJECTS[i] = new byte[(int) (SMALL_OBJECT_SIZE_KB * 1024)];
            }
            for (int i = 1; i < SMALL_OBJECTS_NUM; i += 2) {
                SMALL_OBJECTS[i] = new byte[(int) (SMALL_OBJECT_SIZE_KB * 1024)];
            }
            Thread.sleep(100);
        }
    }
}
