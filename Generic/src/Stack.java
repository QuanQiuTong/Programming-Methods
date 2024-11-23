public class Stack<E> {
    LinkedList<E> list = new LinkedList<E>();

    public E pop() {
        return list.removeLast();
    }

    public E push(E e) {
        list.addLast(e);
        return e;
    }

    public E peek() {
        // return list.get(list.size() - 1);
        return list.back();
    }

    public boolean empty() {
        return list.size() == 0;
    }
}
