package zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher;

public class TestZnode implements Watcher {

	public static void main(String[] args) {
		TestZnode testznode = new TestZnode();
		testznode.create();
	}
	
	public void create() {
		try {
			ZooKeeper zk = new ZooKeeper("99.6.137.76:2182", 3000, this);
			while (zk.getState() != ZooKeeper.States.CONNECTED) {
				synchronized(this) {
					wait();
				}
			}
			if (zk.exists("/mscServiceList", false) == null) {
				// 创建一个目录节点
				zk.create("/mscServiceList", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.PERSISTENT); 
			}
			// 创建一个子目录节点
			zk.create("/mscServiceList/testChildPathOne", "testChildDataOne".getBytes(),
					Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL); 
			System.out.println(new String(zk.getData("/mscServiceList",false,null))); 
			// 取出子目录节点列表
			System.out.println(zk.getChildren("/mscServiceList",true)); 
			// 修改子目录节点数据
			zk.setData("/mscServiceList/testChildPathOne","modifyChildDataOne".getBytes(),-1); 
			System.out.println("目录节点状态：["+zk.exists("/mscServiceList",true)+"]"); 
			// 创建另外一个子目录节点
			zk.create("/mscServiceList/testChildPathTwo", "testChildDataTwo".getBytes(), 
					Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL); 
//			System.out.println(new String(zk.getData("/mscServiceList/testChildPathTwo",true,null)));
//			// 删除子目录节点
//			zk.delete("/mscServiceList/testChildPathTwo",-1); 
//			zk.delete("/mscServiceList/testChildPathOne",-1); 
//			// 删除父目录节点
//			zk.delete("/mscServiceList",-1); 
			// 关闭连接
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void process(WatchedEvent event) {
        System.out.println("已经触发了" + event.getType() + "事件！");
        synchronized(this) {
        	notifyAll();
        }
	}
}
