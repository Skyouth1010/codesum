/**
 * 
 */
package algorithm.sort;

/**
 * @author skyouth
 *
 */
public class QuickSort implements SortStrategy<String> {

	/* (non-Javadoc)
	 * @see algorithm.sort.SortStrategy#sort(algorithm.sort.Element[])
	 */
	@Override
	public void sort(Element<String>[] elements) {
		if (elements == null || elements.length <= 1) return;
		quickSort(elements, 0, elements.length-1);
	}
	
	private void quickSort(Element<String>[] elements, int start, int end) {
		if (start >= end) return;
		int q = partition(elements, start, end);// 分割子数组
		quickSort(elements, start, q-1);// 左边子数组快排
		quickSort(elements, q+1, end);// 右边子数组快排
	}
	
	private int partition(Element<String>[] elements, int start, int end) {
		Element<String> e = elements[end];
		int i = start - 1;
		for (int j = start;j < end;j++) {
			if (elements[j].compareTo(e) <= 0) {
				i++;
				exchange(elements, i, j);
			}
		}
		exchange(elements, i+1, end);
		return i+1;
	}
	
	private void exchange(Element<String>[] elements, int i, int j) {
		Element<String> temp = elements[j];
		elements[j] = elements[i];
		elements[i] = temp;
	}
	
	public static void main(String[] args) {
		SortStrategy<String> s = new QuickSort();
		Element<String>[] elements = Arrays.arrays();
		s.sort(elements);
		

		StringBuilder sb = new StringBuilder();
		for (Element<String> e : elements) {
			sb.append(e.key);
			sb.append(",");
		}
		System.out.println(sb.toString());
	}

}
