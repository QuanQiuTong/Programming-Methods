public class ThreadSync {
    public static void main(String[] args) {
        ThreadA thread1 = new ThreadA();
        ThreadB thread2 = new ThreadB();
        
        thread1.start();
        thread2.start();
    }
}
