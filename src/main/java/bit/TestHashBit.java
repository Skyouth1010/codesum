package bit;

public class TestHashBit {

	public static void main(String[] args) {
		
		int n = 1 << 30;
		int count = 0;
		long start = System.currentTimeMillis();
		for (int i = 1; i < n; i = (i << 1)) {
			int m = 0;
			for (int j = 0; j < 2*i; j=j+5) {
				m = hash(j) & (i - 1);
				count++;
			}
		}
		System.out.println(count);
		System.out.println(System.currentTimeMillis() - start);
		count = 0;
		start = System.currentTimeMillis();
		for (int i = 1; i < n; i = (i << 1)) {
			int m = 0;
			for (int j = 0; j < 2*i; j=j+5) {
				m = hash(j) % i;
				count++;
			}
		}
		System.out.println(count);
		System.out.println(System.currentTimeMillis() - start);
//		}
	}
	
	static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
}
