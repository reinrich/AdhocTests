public class TestMonitorEnter {

    public static int dummyCounter;

    public static void main(String[] args) {
        Object lock = new Object();
        for (int i = 0; i < 30_000; i++) {
            dontinline_testMethod(lock);
        }
    }

    public static void dontinline_testMethod(Object lock) {
        synchronized (lock) {
            dummyCounter++;
        }
    }
}