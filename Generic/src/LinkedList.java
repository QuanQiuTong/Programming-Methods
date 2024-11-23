public class LinkedList<T> {
    private class Node {
        T data;
        Node next;
        Node prev;
    }

    private Node head;
    private Node tail;
    private int size;

    protected T back() {
        return tail.data;
    }

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void addLast(T data) {
        Node newNode = new Node();
        newNode.data = data;
        newNode.next = null;
        newNode.prev = tail;

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T removeLast() {
        if (tail == null) {
            return null;
        }

        T data = tail.data;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;

        return data;
    }

    public boolean contains(T data) {
        Node current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public T get(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                return null;
            }
            current = current.next;
        }
        return current.data;
    }

    public int size() {
        return size;
    }
}