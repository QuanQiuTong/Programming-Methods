public class EmuExe {
    public static int executeCount = 0;

    /**
     * 模拟执行一个任务。
     * 
     * 为了显示多个线程可以同时执行，该函数会记录当前正在执行任务的线程数量。
     * 将其输出即可验证是否有多个线程同时执行。
     */
    public static void execute() {
        synchronized (EmuExe.class) {
            executeCount++;
            if(executeCount > 1) {
                // System.out.println("Threads running concurrently: " + executeCount);
            }
        }
        
        try {
            Thread.sleep((int) (Math.random() * 100));
        } catch (Exception e) {
        }

        synchronized (EmuExe.class) {
            executeCount--;
        }
    }
}
