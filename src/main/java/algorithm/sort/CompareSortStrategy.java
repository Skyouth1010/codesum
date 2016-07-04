package algorithm.sort;

public class CompareSortStrategy {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Element<String>[] elements = Arrays.arrays();
		
		Element<String>[] msarrays = new Element[elements.length];
		System.arraycopy(elements, 0, msarrays, 0, elements.length);
		
		Element<String>[] hsarrays = new Element[elements.length];
		System.arraycopy(elements, 0, hsarrays, 0, elements.length);
		
		Element<String>[] isarrays = new Element[elements.length];
		System.arraycopy(elements, 0, isarrays, 0, elements.length);
		
		SortStrategy<String> ms = new MergeSort();
		SortStrategy<String> hs = new HeapSort();
		SortStrategy<String> is = new InsertSort();
		
		long now = System.currentTimeMillis();
		is.sort(isarrays);
		System.out.println(System.currentTimeMillis() - now);// 插入排序耗时
		now = System.currentTimeMillis();
		
		ms.sort(isarrays);
		System.out.println(System.currentTimeMillis() - now);// 归并排序耗时
		now = System.currentTimeMillis();
		
		hs.sort(isarrays);
		System.out.println(System.currentTimeMillis() - now);// 堆排序耗时
	}

}
