
## `EmuExe.java`
   
可以显示确实有多个线程同时运行，  
只须解开`System.out.println`这一行的注释即可。

```java
public class EmuExe {
    public static int executeCount = 0;
    public static void execute() {
        synchronized (EmuExe.class) {
            executeCount++;
            if(executeCount > 1) {
                System.out.println("Threads running concurrently: " + executeCount);
            }
        }
        
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (Exception e) {
        }

        synchronized (EmuExe.class) {
            executeCount--;
        }
    }
}
```