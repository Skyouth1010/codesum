package concurrent;

public class AtomicVisibilityTest {
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i< 1<<20;i++) {
			DataObject da = new DataObject();
			Thread t1 = new ThreadTest2(da);
			t1.start();
			Thread t2 =new ThreadTest1(da);
			t2.start();
			while(t1.isAlive() || t2.isAlive()) {
				Thread.sleep(10);
			}
		}
	}
	
	public static void addi(DataObject da, int n) {
		da.setCnt(n);
		da.setReady(true);
	}
	
	static class ThreadTest1 extends Thread {
		
		DataObject da;
		
		public ThreadTest1(DataObject da) {
			this.da = da;
		}
		
		@Override
		public void run() {
			addi(da, 5);
			System.out.println("set done~");
		}
	}
	
	static class ThreadTest2 extends Thread {
		DataObject da;
		
		public ThreadTest2(DataObject da) {
			this.da = da;
		}
		
		@Override
		public void run() {
			while(!da.isReady() || da.getCnt() != 5)
				// TODO 如果有这个打印就没有可见性问题，去掉这个打印，就有可见性问题，百思不得其解啊。。
				System.out.println("fuck");
			
			System.out.println("yeah!!!!!!!!!!!!!!!!!!!!!" + da.isReady() + da.getCnt());
		}
	}

}
