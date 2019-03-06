public class LinkedListQueue<E> implements Queue<E> {

    private SinglyLinkedList<E> q;

    public LinkedListQueue() {
        q = new SinglyLinkedList<>();
    }

    public int size() {
        return q.size();
    }

    public boolean isEmpty() {
        return q.isEmpty();
    }

    public E first() {
        return q.first();
    }

    public void enqueue(E node) {
        q.addLast(node);
    }

    public E dequeue() {
        return q.removeFirst();
    }
}
