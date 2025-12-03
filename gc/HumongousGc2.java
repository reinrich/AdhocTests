public class HumongousGc2 {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 100; i++) {
            Object[] h1 = new Object[10_000_000];
            Object[] h2 = new Object[10_000_000];
            h1[0] = h2;
            h2[0] = h1;
            System.out.println(i);
            Thread.sleep(100);
        }
    }
}
