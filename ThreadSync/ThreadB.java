public class ThreadB extends Thread {
    public static volatile int B = 100;

    private void decB() {
        B--;
        EmuExe.execute();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {

            // 同步代码
            synchronized (SharedState.lock) {
                // 等待线程A完成第i-1次迭代
                while (SharedState.iterationA < i - 1) {
                    System.out.println("Thread B waiting for A");
                    try {
                        SharedState.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 检查 A + B 是否等于 100
                if (ThreadA.A + B != 100) {
                    System.out.println("Error! A + B = " + (ThreadA.A + B));
                    System.out.println(" A = " + ThreadA.A + ", B = " + B);
                }

                // 标记已完成检查
                SharedState.checkedB = true;
                SharedState.lock.notifyAll();

                // 等待线程A完成检查
                // 必须等待，否则可能导致线程A在B完成检查之前就改变了A的值
                while (!SharedState.checkedA) {
                    try {
                        SharedState.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("* B: " + B);

            // 执行 decB()。要验证其可以与A并行执行，可以参照README.md修改EmuExe.java
            decB();

            // 同步代码，更新 iterationB，并通知线程A
            synchronized (SharedState.lock) {
                SharedState.iterationB = i;
                SharedState.checkedB = false; // 记得重置标志位
                SharedState.lock.notifyAll();
            }
        }
    }
}