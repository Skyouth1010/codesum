package concurrent;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

	private class ForkJoinTask extends RecursiveTask<Integer> {
		private static final long serialVersionUID = 1L;
		private double[] d;

		public ForkJoinTask setD(double[] d) {
			this.d = d;
			return this;
		}

		private int first;
		private int last;

		public ForkJoinTask(int first, int last) {
			this.first = first;
			this.last = last;
		}

		@Override
		protected Integer compute() {
			int subCount;
			if (last - first < 10) {
				subCount = 0;
				for (int i = first; i <= last; i++) {
					if (d[i] < 0.5)
						subCount++;
				}
			} else {
				int mid = (first + last) >>> 1;
				ForkJoinTask left = new ForkJoinTask(first, mid).setD(d);
				left.fork();
				ForkJoinTask right = new ForkJoinTask(mid + 1, last).setD(d);
				right.fork();
				subCount = left.join();
				subCount += right.join();
			}
			return subCount;
		}
	}

	public static void main(String[] args) {
		double[] d = createArrayOfRandomDoubles();
		ForkJoinTest forkJoinTest = new ForkJoinTest();
		int n = new ForkJoinPool().invoke(forkJoinTest.new ForkJoinTask(0,
				d.length-1).setD(d));
		System.out.println("Found " + n + " values");
	}
	
	static double[] createArrayOfRandomDoubles() {
		double[] d = new double[2<<2];
		Random r = new Random(1);
		for (int i = 0; i < d.length; i++) {
			d[i] = r.nextDouble();
		}
		return d;
	}

}
