package algorithm.sort;

/**
 * @author skyouth
 *
 * 堆排序，使用最大堆
 * 最大堆性质是指除了根以外的所有结点i都要满足：T[parent(i)]>=T[i]
 * @param <T>
 */
public class HeapSort implements SortStrategy<String> {

	@Override
	public void sort(Element<String>[] elements) {
		HeapArray array = new HeapArray(elements);
		if (array.arrays == null || array.arrays.length <= 1) return;
		buildMaxHeap(array);
		for (int i = array.arrays.length-1; i>=0; i--) {
			array.exchange(0, i);
			array.heapSize--;
			maxHeapify(array, 0);
		}
	}
	
	/**
	 * 建堆(最大堆)
	 * 根据堆的定义，当用数组表示存储n个元素的堆时，叶结点下标分别是floor(n/2),floor(n/2)+1,...,n-1
	 * @param array
	 */
	void buildMaxHeap(HeapArray array) {
		array.heapSize = array.arrays.length;
		for (int i = array.arrays.length/2-1; i>=0; i--) {
			//叶结点下标分别是floor(n/2),floor(n/2)+1,...,n-1,所以只需从floor(n/2)-1往前建堆
			maxHeapify(array, i);
		}
	}
	
	/**
	 * 维护堆的性质
	 * 假定跟节点T(i)的左子树left(i)和右子树right(i)都已经是最大堆，而T(i)可能小于其孩子，这样就违背了最大堆的性质
	 * 通过此方法让T(i)的值在最大堆中逐级下降，从而使整棵树遵循最大堆的性质
	 * @param arrays
	 * @param i
	 */
	void maxHeapify(HeapArray array, int i) {
		int largest = i;
		int l = left(i);
		int r = right(i);
		if (l < array.heapSize && array.get(l).compareTo(array.get(i)) > 0)
			largest = l;
		if (r < array.heapSize && array.get(r).compareTo(array.get(largest)) > 0)
			largest = r;
		if (largest != i) {
			array.exchange(largest, i);
			maxHeapify(array, largest);
		}
		
	}
	
	int parent(int i) {
		return (i-1) >> 1;
	}
	
	int left(int i) {
		return (i << 1) + 1;
	}
	
	int right(int i) {
		return (i+1) << 1;
	}
	
	private class HeapArray {

		int heapSize;
		
		Element<String>[] arrays;
		
		public HeapArray(Element<String>[] elements) {
			arrays = elements;
			heapSize = arrays.length;
		}
		
		Element<String> get(int i) {
			return arrays[i];
		}
		
		void exchange(int i, int j) {
			Element<String> temp = arrays[j];
			arrays[j] = arrays[i];
			arrays[i] = temp;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (Element<String> e : arrays) {
				sb.append(e.key);
				sb.append(",");
			}
			return sb.toString();
		}
	}
	
	public static void main(String[] args) {
		SortStrategy<String> s = new HeapSort();
		Element<String>[] elements = Arrays.arrays();
		s.sort(elements);
		System.out.println(new HeapSort().new HeapArray(elements));
	}
}
