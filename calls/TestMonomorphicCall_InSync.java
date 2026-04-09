public class TestMonomorphicCall_InSync {

    public static void main(String[] args) {
        Object lock = new Object();
        MonoReceiver recv = new MonoReceiver();
        for (int i = 0; i < 30_000; i++) {
            dontinline_testMethod(lock, recv);
        }
    }

    public static int dontinline_testMethod(Object lock, MonoReceiver receiver) {
        int res = 0;
        synchronized (lock) {
            res = receiver.targetMethod();
        }
        return res;
    }

    public static class MonoReceiver {
        public int targetMethod() {
            return 42;
        }
    }
}
