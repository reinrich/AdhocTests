public class HumongousGc_int {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100; i++) {
            int[] humongous = new int[10_000_000];
            System.out.println(i);
            Thread.sleep(100);
        }
    }
}
