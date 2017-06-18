package concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BeeperControl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BeeperControl bc = new BeeperControl();
		bc.beepForAnHour();
		synchronized (bc) {
			try {
				bc.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("shutdown scheduler");
		if (bc.scheduler != null && !bc.scheduler.isShutdown()) {
			bc.scheduler.shutdownNow();
		}
	}

	final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	public void beepForAnHour() {
		final Runnable beeper = new Runnable() {
			public void run() {
				System.out.println("beep");
			}
		};
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(
				beeper, 2, 2, TimeUnit.SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {
				beeperHandle.cancel(true);
				System.out.println("cancel");
				synchronized (BeeperControl.this) {
					BeeperControl.this.notify();
				}
			}
		}, 2 * 2, TimeUnit.SECONDS);
	}
	
	

}
