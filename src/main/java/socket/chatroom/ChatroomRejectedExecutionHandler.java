package socket.chatroom;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class ChatroomRejectedExecutionHandler implements
		RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

		System.out.println("this member is discarded");
	}

}
