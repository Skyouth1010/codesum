package concurrent;
import java.util.ArrayList;
import java.util.List;

public class BlockingQueueTest {

	public static void main(String[] args) {
		
		List<String> strings = new ArrayList<String>();
		strings.add("a");
		strings.add("b");
		strings.add("c");
		
		BlockingQueueTest test = new BlockingQueueTest();
		
        for (String e : strings) {
            if (e == null)
                throw new NullPointerException();
            test.enqueue(new Node(e));
        }
	}
	
	public BlockingQueueTest() {
		last = head = new Node(null);
	}
	
    static class Node {
        String item;

        /**
         * One of:
         * - the real successor Node
         * - this Node, meaning the successor is head.next
         * - null, meaning there is no successor (this is the last node)
         */
        Node next;

        Node(String x) { item = x; }
    }
    private transient Node head;

    /**
     * Tail of linked list.
     * Invariant: last.next == null
     */
    private transient Node last;
    
    private void enqueue(Node node) {
        // assert putLock.isHeldByCurrentThread();
        // assert last.next == null;
        last.next = node;
        last = last.next;
    }
}
