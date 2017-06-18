package zookeeper.serviceCenter.client;

import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class MscServiceChildWatch implements Watcher {
	
	MscServiceClient msc;
	ZooKeeper zk;
	public MscServiceChildWatch(MscServiceClient msc, ZooKeeper zk) {
		this.msc = msc;
		this.zk = zk;
	}

	@Override
	public void process(WatchedEvent event) {
		try {
			List<String> mscService = zk.getChildren("/mscServiceList", true);
			Collections.sort(mscService);
			System.out.println(mscService);
			synchronized (msc) {
				msc.notifyAll();
			}
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
