package zookeeper.serviceCenter.server;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class MscService implements Runnable, Watcher {

	private int num;
	CyclicBarrier barrier;
	@Override
	public void process(WatchedEvent event) {
		System.out.println("MscService"+num+" changed:"+event.getType());
		synchronized(this) {
        	notifyAll();
        }
	}
	
	public MscService(int i, CyclicBarrier barrier) {
		this.num=i;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		try {
			while(true) {
				ZooKeeper zk = new ZooKeeper("99.6.137.76:2182", 3000, this);
				while (zk.getState() != ZooKeeper.States.CONNECTED) {
					synchronized(this) {
						wait();
					}
				}
				if (zk.exists("/mscServiceList", true) == null) {
					// 节点列表不存在，退出
					break;
				}
//				zk.getChildren("/mscServiceList", new Watcher() {
//					@Override
//					public void process(WatchedEvent event) {
//						System.out.println("watch /mscServiceList child:" + " changed");
//					}
//				});
				// 注册本服务
				zk.create("/mscServiceList/mscService"+num, ("mscService"+num).getBytes(),
						Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
//				zk.getData("/mscServiceList/mscService"+num,new Watcher() {
//					@Override
//					public void process(WatchedEvent event) {
//						System.out.println("watch /mscServiceList/mscService"+num + ": changed");
//					}
//				},null);
				barrier.await();
				// 随机关闭一个服务
				Thread.sleep(new Random().nextInt(6000));
				// 关闭本服务
				zk.close();
				barrier.await();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class ChildWatcher implements Watcher {
		ZooKeeper zk;
		MscService ms;
		public ChildWatcher(MscService ms, ZooKeeper zk) {
			this.zk = zk;
			this.ms = ms;
		}
		@Override
		public void process(WatchedEvent event) {
			System.out.println("ChildWatcher"+num+" Registered.");
			List<String> mscService;
			try {
				mscService = zk.getChildren("/mscServiceList", true);
				Collections.sort(mscService);
				System.out.println(mscService);
				synchronized(ms) {
					ms.notifyAll();
				}
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
