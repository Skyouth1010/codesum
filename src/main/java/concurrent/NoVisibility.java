package concurrent;

public class NoVisibility {

	private static boolean ready;
	private static int number;
	
	
	private static class ReaderThread extends Thread {
		public void run() {
			while(!ready) Thread.yield();
			System.out.println(number);
			while(true);
		}
	}
	
	public static void main(String[] args) {
		Thread t = new ReaderThread();
		t.setDaemon(false);
		t.start();
		number = 42;
		ready = true;
	}
}
