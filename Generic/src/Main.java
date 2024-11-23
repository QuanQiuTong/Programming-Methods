class Test {
    static int count = 0;
    int id;

    public Test() {
        count++;
        id = count;
        System.out.println("Test: " + count);
    }

    public void finalize() {
        count--;
        System.out.println("Finalize: " + count);
    }

    @Override
    public String toString() {
        return "Test[" + id + "]";
    }
}

public class Main {

    public static void main(String[] args) throws Exception {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.empty());

        Stack<String> stack2 = new Stack<String>();
        stack2.push("Hello");
        stack2.push("World");
        System.out.println(stack2.peek());
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
        System.out.println(stack2.empty());

        Stack<Test> stack3 = new Stack<Test>();
        stack3.push(new Test());
        stack3.push(new Test());
        stack3.push(new Test());
        System.out.println(stack3.peek());
        System.out.println(stack3.pop());
        System.out.println(stack3.pop());
        System.out.println(stack3.pop());
        System.out.println(stack3.empty());

        System.gc();
    }
}
