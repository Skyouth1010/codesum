package algorithm;

public class Fibonacci {

	public static void main(String[] args) {
		// 1 1 2 3 5 8 13
		int n = 30;
		System.out.println(fRecursion(n));
		System.out.println(fNonRecursion(n));
	}
	
	
	public static int fRecursion(int n) {
		if (n<=0) {
			return -1;
		}
		if (n==1||n==2) return 1;
		return fRecursion(n-1)+fRecursion(n-2);
	}
	
	public static int fNonRecursion(int n) {
		if (n<=0) {
			return -1;
		}
		if (n==1||n==2) return 1;
		
		int a=1,b=1;
		int result = 0;
		
		while(n>2) {
			result = a + b;
			a = b;
			b = result;
			n--;
		}
		return result;
	}
}
