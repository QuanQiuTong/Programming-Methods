public class ThreadSyncTen {
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];

        // 五个自增线程 A-E
        for (int i = 0; i < 5; i++) {
            threads[i] = new IncrementThread(i);
        }
        // 五个自减线程 F-J
        for (int i = 5; i < 10; i++) {
            threads[i] = new DecrementThread(i);
        }

        for (Thread t : threads) {
            t.start();
        }
    }

}
