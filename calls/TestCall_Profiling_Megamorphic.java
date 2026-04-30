public class TestCall_Profiling_Megamorphic {

    private static final String INPUT = "input"; // E.g. -Dinput=3

    public static void main(String[] args) {
        ReceiverStaticType recv_S = new ReceiverStaticType();
        ReceiverStaticType recv_A = new ReceiverDynamicType_A();
        ReceiverStaticType recv_B = new ReceiverDynamicType_B();
        int calls = 30_000;
        System.out.println("### WARMUP (calls: " + calls + ")");
        System.out.println("### recv_S.getClass(): " + recv_S.getClass());
        System.out.println("### recv_A.getClass(): " + recv_A.getClass());
        System.out.println("### recv_B.getClass(): " + recv_B.getClass());
        for (int i = 0; i < calls; i++) {
            dontinline_testMethod(recv_S);
            dontinline_testMethod(recv_A);
            dontinline_testMethod(recv_B);
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

    public static class ReceiverDynamicType_A extends ReceiverStaticType {
        @Override
        public int dontinline_targetMethod() {
            return 2;
        }
    }

    public static class ReceiverDynamicType_B extends ReceiverStaticType {
        @Override
        public int dontinline_targetMethod() {
            return 2;
        }
    }
}
