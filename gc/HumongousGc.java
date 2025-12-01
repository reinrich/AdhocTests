public class HumongousGc {
    public static void main(String[] args) throws Exception {
        if (args.length > 0) System.gc();
        for (int i = 0; i < 100; i++) {
            Integer[] humongous = new Integer[10_000_000];
            System.out.println(i);
            Thread.sleep(100);
        }
    }
}
