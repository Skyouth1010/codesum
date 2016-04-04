package concurrent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

	public static void main(String[] args) {

		float matrix[][] = new float[][] { new float[] { 1, 2, 3 },
				new float[] { 11, 12, 13 }, new float[] { 21, 22, 23 } };

		new CyclicBarrierTest(matrix);

	}

	class Worker implements Runnable {
		int myRow;

		Worker(int row) {
			myRow = row;
		}

		public void run() {
			while(!done()) {
				processRow(myRow);
				try {
					System.out.println(myRow + " done!");
					barrier.await();
				} catch (Exception ex) {
					return;
				}
			}
		}
	}

	public CyclicBarrierTest(float[][] matrix) {
		data = matrix;
		N = matrix.length;
		barrier = new CyclicBarrier(N, new Runnable() {
			public void run() {
				System.out.println("done! add!");
				mergeRows();
			}
		});
		for (int i = 0; i < N; ++i)
			new Thread(new Worker(i)).start();
	}

	public void processRow(int myRow) {
		float mm = 0;
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		for (float m : data[myRow]) {
			mm += m;
		}
		f.add(mm);
	}
	
	public boolean done() {
		while (System.currentTimeMillis() - start > 1000) {
			return true;
		}
		return false;
	}

	public void mergeRows() {
		float mm = 0;
		for (Float fl : f) {
			mm += fl;
		}
		System.out.println("ans : " + mm);
	}

	public void waitUntilDone() {
		System.out.println("waitUntilDone");
		while (barrier.getNumberWaiting() > 0) {
			System.out.println(barrier.getNumberWaiting());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	final List<Float> f = Collections.synchronizedList(new ArrayList<Float>());
	final long start = System.currentTimeMillis();
	final int N;
	final float[][] data;
	final CyclicBarrier barrier;
}
