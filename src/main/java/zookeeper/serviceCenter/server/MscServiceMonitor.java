package zookeeper.serviceCenter.server;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class MscServiceMonitor implements Watcher {
	
	String input ;
	ZooKeeper zk;
	int num;
	public MscServiceMonitor(String input, int num) {
		this.input = input;
		this.num = num;
	}
	
	public void start() {
		try {
			zk = new ZooKeeper("99.6.137.76:2182", 3000, this);
			while (zk.getState() != ZooKeeper.States.CONNECTED) {
				synchronized(this) {
					wait();
				}
			}
			if (zk.exists("/mscServiceList", true) == null) {
				// 节点列表不存在，退出
				return;
			}
			// 注册本服务
			zk.create("/mscServiceList/mscService"+num, ("mscService"+num).getBytes(),
					Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		try {
			if (zk != null && zk.getState() != ZooKeeper.States.CLOSED) {
				zk.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(WatchedEvent event) {
		System.out.println("MscService"+num+" changed:"+event.getType());
		synchronized(this) {
        	notifyAll();
        }
	}
}
