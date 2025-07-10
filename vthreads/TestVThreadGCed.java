import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class TestVThreadGCed {

    static ReferenceQueue<Thread> refQueue;
    static PhantomReference<Thread> phantom;

    static Object lock = new Object();

    public static void main(String[] args) {
        try {
            startVirtualThread();

            // lock = null;
            doGCAndCheck();

            synchronized (lock) {
                        System.out.println("Calling notify()");
                        lock.notify();
            }

            doGCAndCheck();
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    public static void startVirtualThread() throws InterruptedException {
        var vthread = Thread.startVirtualThread(() -> {
                synchronized (lock) {
                    try {
                        System.out.println("Calling wait()");
                        lock.wait();
                    } catch (InterruptedException e) { /* ign */ }
                }
            });

        refQueue = new ReferenceQueue<>();
        phantom = new PhantomReference<>(vthread, refQueue);
        Thread.sleep(100);
    }

    public static void doGCAndCheck() throws InterruptedException {
        for (int i = 10; i > 0; i--) {
            System.out.println("System.gc()");
            System.gc();
            Thread.sleep(100);
        }

        if (refQueue.remove(1000) != null) {
            System.out.println("VirtualThread *was* garbage collected!");
            return;
        }
        System.out.println("VirtualThread was *not* garbage collected!");
    }
}
