public class ThreadA extends Thread {
    public static volatile int A = 0;

    private void incA() {
        A++;
        EmuExe.execute();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {

            // 同步代码
            synchronized (SharedState.lock) {
                // 等待线程B完成第i-1次迭代
                while (SharedState.iterationB < i -1) {
                    System.out.println("Thread A waiting for B");
                    try {
                        SharedState.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 检查 A + B 是否等于 100
                if (A + ThreadB.B != 100) {
                    System.out.println("Error! A + B = " + (A + ThreadB.B));
                    System.out.println(" A = " + A + ", B = " + ThreadB.B);
                }

                // 标记已完成检查
                SharedState.checkedA = true;
                SharedState.lock.notifyAll();

                // 等待线程B完成检查
                // 必须等待，否则可能导致线程B在A完成检查之前就改变了B的值
                while (!SharedState.checkedB) {
                    try {
                        SharedState.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            System.out.println("* A: " + A);

            // 执行 incA()。要验证其可以与B并行执行，可以参照README.md修改EmuExe.java
            incA();

            // 同步代码，更新 iterationA，并通知线程B
            synchronized (SharedState.lock) {
                SharedState.iterationA = i;
                SharedState.checkedA = false; // 记得重置标志位
                SharedState.lock.notifyAll();
            }
        }
    }
}