package concurrent.net.jcip.examples;

/**
 * BarrierTimer
 * <p/>
 * Barrier-based timer
 *
 * @author Brian Goetz and Tim Peierls
 */
public class BarrierTimer implements Runnable {
    private boolean started;
    private long startTime, endTime;

    public synchronized void run() {
        long t = System.nanoTime();
        if (!started) {
        	System.out.print(" BarrierTimer run start ..");
            started = true;
            startTime = t;
        } else {
        	System.out.print(" end ..");        	
        	endTime = t;
        }
    }

    public synchronized void clear() {
        started = false;
    }

    public synchronized long getTime() {
        return endTime - startTime;
    }
}
