package zookeeper.serviceCenter.client;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class MscServiceClient implements Runnable, Watcher {

	ZooKeeper zk;
	Object object = new Object();
	
	@Override
	public void run() {
		this.start();
	}
	
	public void start() {
		try {
			zk = new ZooKeeper("99.6.137.76:2182", 3000, this);
			while (zk.getState() != ZooKeeper.States.CONNECTED) {
				synchronized(this) {
					wait();
				}
			}
			while(true) {
				zk.getChildren("/mscServiceList", new MscServiceChildWatch(this, zk));
				synchronized (this) {
					wait();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MscServiceClient client = new MscServiceClient();
		new Thread(client).start();
	}

	@Override
	public void process(WatchedEvent event) {
//		System.out.println("MscServiceClient start:"+event.getType());
		synchronized(this) {
        	notifyAll();
        }
	}

}
