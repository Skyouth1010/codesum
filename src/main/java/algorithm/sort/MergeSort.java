/**
 * 
 */
package algorithm.sort;

/**
 * @author skyouth
 * 归并排序
 *
 */
public class MergeSort implements SortStrategy<String> {

	/* (non-Javadoc)
	 * @see algorithm.sort.SortStrategy#sort(algorithm.sort.Element[])
	 */
	@Override
	public void sort(Element<String>[] elements) {
		
		if (elements == null || elements.length == 1) return;
		int mid = elements.length/2;
		@SuppressWarnings("unchecked")
		Element<String>[] elementsL = new Element[mid];
		System.arraycopy(elements, 0, elementsL, 0, mid);
		
		@SuppressWarnings("unchecked")
		Element<String>[] elementsR = new Element[elements.length-mid];
		System.arraycopy(elements, mid, elementsR, 0, elements.length-mid);
		
		// 左右分别进行排序
		sort(elementsL);
		sort(elementsR);
		
		// 归并
		int l = 0;// left array,0,1,2...,mid-1
		int r = 0;// left array,0,1,2...,elements.length-mid-1
		int c = 0;
		for (; c < elements.length && l < mid && r < elements.length-mid; c++) {
			if (elementsL[l].compareTo(elementsR[r]) < 0) {
				elements[c] = elementsL[l];
				l++;
			} else {
				elements[c] = elementsR[r];
				r++;
			}
		}
		
		if (l > r) System.arraycopy(elementsR, r, elements, c, elements.length-mid - r);
		else if (l < r) System.arraycopy(elementsL, l, elements, c, mid - l);
	}
	
	public static void main(String[] args) {
		SortStrategy<String> s = new MergeSort();
		Element<String>[] elements = Arrays.arrays();
		s.sort(elements);
		

		StringBuilder sb = new StringBuilder();
		for (Element<String> e : elements) {
			sb.append(e.key);
			sb.append(",");
		}
//		System.out.println(sb.toString());
	}
}
