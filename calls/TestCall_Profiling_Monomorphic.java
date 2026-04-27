public class TestCall_Profiling_Monomorphic {

    public static void main(String[] args) {
        ReceiverStaticType recv = new ReceiverDynamicType();
        for (int i = 0; i < 30_000; i++) {
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
