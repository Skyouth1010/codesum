package algorithm.sort;

import java.util.Random;

public class Arrays {
	
	static final int NUM = (1 << 17) + 1;
	
	private Arrays() {}
	
	public static <T> Element<T>[] arrays() {
		@SuppressWarnings("unchecked")
		Element<T>[] arrays = new Element[NUM];
		// 此处不能是Element<T>[] arrays = new Element<T>[num];java不支持泛型数组
		int i = 0;
		while(i < NUM) {
			arrays[i] = new Element<T>();
			arrays[i].key = new Random().nextInt(NUM);
			i++;
		}
		return arrays;
	}
}

final class Element<T> implements Comparable<Element<T>> {
	int key;
	
	T value;

	@Override
	public int compareTo(Element<T> o) {
		return key - o.key;
	}
	
	@Override
	public String toString() {
		return "Element(" + key + "," + value + ")";
	}
}