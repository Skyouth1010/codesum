package algorithm;

public class QuickSort {

	public static void main(String[] args) {

		long[] x = {7L,1L,4L,7L,3L,8L,7L,5L,2L,7L,6L,9L,7L};
		for (long t : x) System.out.print(t); 
		System.out.println(x);
		quickSort(x, 0, x.length);
		for (long t : x) System.out.print(t); 
		System.out.println(x);
	}
	
    private static void quickSort(long x[], int off, int len) {
	// Insertion sort on smallest arrays
	if (len < 7) {
	    for (int i=off; i<len+off; i++)
		for (int j=i; j>off && x[j-1]>x[j]; j--)
		    swap(x, j, j-1);
	    return;
	}

	// Choose a partition element, v, randomized-version partition for quick-sort
	int m = off + (len >> 1);       // Small arrays, middle element
	if (len > 7) {
	    int l = off;
	    int n = off + len - 1;
	    if (len > 40) {        // Big arrays, pseudomedian of 9
		int s = len/8;
		l = med3(x, l,     l+s, l+2*s);
		m = med3(x, m-s,   m,   m+s);
		n = med3(x, n-2*s, n-s, n);
	    }
	    m = med3(x, l, m, n); // Mid-size, med of 3
	}
	long v = x[m];

	// Establish Invariant: v* (<v)* (>v)* v*，即和v相等的都放两边
	// a和d就是两端v的坐标，b和c是比较的坐标，两边向内收缩
	int a = off, b = a, c = off + len - 1, d = c;
	while(true) {
	    while (b <= c && x[b] <= v) {
		if (x[b] == v)
		    swap(x, a++, b);
		b++;
	    }
	    while (c >= b && x[c] >= v) {
		if (x[c] == v)
		    swap(x, c, d--);
		c--;
	    }
	    if (b > c)
		break;
	    swap(x, b++, c--);
	}

	// Swap partition elements back to middle : (<v)*v*(>v)*，将相等的v都放回到中间去
	int s, n = off + len;
	s = Math.min(a-off, b-a  );
	vecswap(x, off, b-s, s);//左边的v拷贝到中间
	s = Math.min(d-c,   n-d-1);
	vecswap(x, b,   n-s, s);//右边的v拷贝到中间

	// Recursively sort non-partition-elements，左右两边递归进行快排
	if ((s = b-a) > 1)
		quickSort(x, off, s);// 左边进行快排
	if ((s = d-c) > 1)
		quickSort(x, n-s, s);// 右边进行快排
    }
    
    private static void swap(long x[], int a, int b) {
	long t = x[a];
	x[a] = x[b];
	x[b] = t;
    }
    
    private static void vecswap(long x[], int a, int b, int n) {
	for (int i=0; i<n; i++, a++, b++)
	    swap(x, a, b);
    }
    
    private static int med3(long x[], int a, int b, int c) {
	return (x[a] < x[b] ?
		(x[b] < x[c] ? b : x[a] < x[c] ? c : a) :
		(x[b] > x[c] ? b : x[a] > x[c] ? c : a));
    }

}
