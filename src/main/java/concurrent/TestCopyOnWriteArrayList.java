package concurrent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestCopyOnWriteArrayList {

	public static void main(String[] args) {
		List<Point> list = new CopyOnWriteArrayList<Point>();
		list.add((new TestCopyOnWriteArrayList()).new Point(1, 2));
		System.out.println(list);
		list.get(0).x = 2;
		System.out.println(list);
	}
	
	class Point {
		public int x,y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public String toString() {
			return x + " " + y;
		}
	}

}
