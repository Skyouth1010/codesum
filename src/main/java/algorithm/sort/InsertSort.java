package algorithm.sort;

/**
 * @author skyouth
 * 插入排序
 *
 */
public class InsertSort implements SortStrategy<String> {

	/* (non-Javadoc)
	 * @see algorithm.sort.SortStrategy#sort(algorithm.sort.Element[])
	 */
	@Override
	public void sort(Element<String>[] elements) {
		if (elements == null || elements.length <= 1) return;
		
		for (int i = 1; i< elements.length; i++) {
			// 将第i个元素和0 到 i－1 个元素进行比较，一旦其中第j个元素<该元素，插入其中
			Element<String> key = elements[i];
			int j = i - 1;
			for (; j >= 0; j--) {
				if (elements[j].compareTo(key)>0) {
					elements[j+1] = elements[j];
				} else {
					break;
				}
			}
			elements[j+1] = key;
		}
	}
	
	public static void main(String[] args) {
		SortStrategy<String> s = new InsertSort();
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
