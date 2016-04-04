package zookeeper.serviceCenter.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class MscServiceRegister implements Watcher {
	
	public static void main(String[] args) {

		MscServiceRegister register = new MscServiceRegister();
		register.start();
		register.runMonitor();
	}
	
	@SuppressWarnings("resource")
	public void runMonitor() {
		Map<Integer, MscServiceMonitor> map = new HashMap<Integer, MscServiceMonitor>();
		while(true) {
			Scanner input = new Scanner(System.in);
			String s = input.nextLine();
			if (s.startsWith("start") && s.matches("start \\d+")) {
				Integer num = Integer.valueOf(s.substring(6));
				if (map.get(num) != null) {
					System.out.println("already started");
					continue;
				}
				MscServiceMonitor msm = new MscServiceMonitor(s, num);
				msm.start();
				map.put(num, msm);
			} else if (s.startsWith("shutdown") && s.matches("shutdown \\d+")) {
				Integer num = Integer.valueOf(s.substring(9));
				if (map.get(num) != null) {
					MscServiceMonitor msm = map.get(num);
					msm.shutdown();
					map.remove(num);
					continue;
				}
				System.out.println("already shutdown");
			} else if (s.equals("outofmemory")) {
				Integer[] large = new Integer[1024 * 1024 * 1024];
				System.out.println(large);
			}
		}
	}
	
	public void runConcurrent() {
		int serviceNum = 5;
		
		CyclicBarrier barrier = new CyclicBarrier(serviceNum);
		
		ExecutorService es = Executors.newFixedThreadPool(serviceNum);
		for (int i = 1; i <=serviceNum; i++) {
			MscService mscService = new MscService(i, barrier);
			es.submit(mscService);
		}
	}
	
	public void start() {
		try {
			ZooKeeper zk = new ZooKeeper("99.6.137.76:2182", 3000, this);
			while (zk.getState() != ZooKeeper.States.CONNECTED) {
				synchronized(this) {
					wait();
				}
			}
			if (zk.exists("/mscServiceList", false) == null) {
				// 创建msc服务列表
				zk.create("/mscServiceList", "mscServiceList".getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT);
			}
			// 关闭本服务
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("MscServiceRegister start:"+event.getType());
		synchronized(this) {
        	notifyAll();
        }
	}
}
