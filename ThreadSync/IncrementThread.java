public class IncrementThread extends Thread {
    private int index; // 对应 A-E

    public IncrementThread(int index) {
        this.index = index;
    }

    private void increment() {
        SharedStateTen.values[index]++;
        EmuExe.execute();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Value of " + (char) ('A' + index) + ": " + SharedStateTen.values[index]);

            synchronized (SharedStateTen.lock) {
                // 等待上一轮迭代完成
                while (SharedStateTen.iteration < i - 1) {
                    try {
                        SharedStateTen.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                SharedStateTen.readyCount++;
                // 最后一个完成本轮迭代的线程负责检查总和
                if (SharedStateTen.readyCount == 10) {
                    int sum = 0;
                    for (int val : SharedStateTen.values) {
                        sum += val;
                    }
                    if (sum != 500) {
                        System.out.println("Error! Sum = " + sum);
                    } else {
                        System.out.println("Sum = " + sum);
                    }
                    SharedStateTen.readyCount = 0;
                    SharedStateTen.iteration = i;
                    SharedStateTen.lock.notifyAll();
                } else {
                    try {
                        SharedStateTen.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // 执行增量操作（不在同步块中，可以并行）
            increment();
        }
    }
}
