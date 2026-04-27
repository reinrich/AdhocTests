public class TestCall_Profiling_Monomorphic_then_BiMorphic {

    private static final String INPUT = "input"; // E.g. -Dinput=3

    public static void main(String[] args) {
        ReceiverStaticType recv = new ReceiverDynamicType();
        int calls = 30_000;
        System.out.println("### WARMUP (calls: " + calls + ")");
        System.out.println("### recv.getClass(): " + recv.getClass());
        for (int i = 0; i < calls; i++) {
            dontinline_testMethod(recv);
        }

        calls = Integer.valueOf(System.getProperty(INPUT, "3"));
        recv = new ReceiverStaticType();
        System.out.println("### CHANGE RECEIVER TYPE (calls: " + calls + ")");
        System.out.println("### recv.getClass(): " + recv.getClass());
        for (int i = 0; i < calls; i++) {
            if ((i % 1000) == 0) {
                System.out.println("### made " + i + " calls");
            }
            dontinline_testMethod(recv);
        }
    }

    public static int dontinline_testMethod(ReceiverStaticType receiver) {
        int res = receiver.dontinline_targetMethod();
        return res;
    }

    public static class ReceiverStaticType {
        public int dontinline_targetMethod() {
            return 1;
        }
    }

    public static class ReceiverDynamicType extends ReceiverStaticType {
        @Override
        public int dontinline_targetMethod() {
            return 2;
        }
    }
}
