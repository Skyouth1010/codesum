package concurrent.net.jcip.examples;

import java.util.concurrent.atomic.*;

import concurrent.net.jcip.annotations.*;

/**
 * LinkedQueue
 * <p/>
 * Insertion in the Michael-Scott nonblocking queue algorithm
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class LinkedQueue <E> {

    private static class Node <E> {
        final E item;
        final AtomicReference<LinkedQueue.Node<E>> next;

        public Node(E item, LinkedQueue.Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<LinkedQueue.Node<E>>(next);
        }
    }

    private final LinkedQueue.Node<E> dummy = new LinkedQueue.Node<E>(null, null);
    private final AtomicReference<LinkedQueue.Node<E>> head
            = new AtomicReference<LinkedQueue.Node<E>>(dummy);
    private final AtomicReference<LinkedQueue.Node<E>> tail
            = new AtomicReference<LinkedQueue.Node<E>>(dummy);// 

    public boolean put(E item) {
        LinkedQueue.Node<E> newNode = new LinkedQueue.Node<E>(item, null);
        while (true) {
            LinkedQueue.Node<E> curTail = tail.get();
            LinkedQueue.Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) {//a
                    // Queue in intermediate state, advance tail
                    tail.compareAndSet(curTail, tailNext);//b
                } else {
                    // In quiescent state, try inserting new node
                    if (curTail.next.compareAndSet(null, newNode)) {//c
                        // Insertion succeeded, try advancing tail
                        tail.compareAndSet(curTail, newNode);//d
                        return true;
                    }
                }
            }
        }
    }
}
