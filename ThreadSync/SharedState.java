public class SharedState {
    public static volatile int iterationA = -1;
    public static volatile int iterationB = -1;
    public static boolean checkedA = false;
    public static boolean checkedB = false;
    public static final Object lock = new Object();
}
