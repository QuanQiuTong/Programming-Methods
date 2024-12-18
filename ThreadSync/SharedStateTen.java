public class SharedStateTen {
    public static volatile int iteration = -1; // 当前迭代轮数
    public static volatile int readyCount = 0; // 有多少个线程已经完成了这一轮迭代
    public static final Object lock = new Object();

    // 0-9 分别对应于计数变量 A-J
    public static volatile int[] values = new int[10];

    static {
        // 初始时 A-E 为 0，F-J 为 100
        for (int i = 0; i < 5; i++) {
            values[i] = 0;
        }
        for (int i = 5; i < 10; i++) {
            values[i] = 100;
        }
    }
}
