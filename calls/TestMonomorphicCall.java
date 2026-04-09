public class TestMonomorphicCall {

    public static void main(String[] args) {
        MonoReceiver recv = new MonoReceiver();
        for (int i = 0; i < 30_000; i++) {
            dontinline_testMethod(recv);
        }
    }

    public static int dontinline_testMethod(MonoReceiver receiver) {
        int res = receiver.targetMethod();
        return res;
    }

    public static class MonoReceiver {
        public int targetMethod() {
            return 42;
        }
    }
}
