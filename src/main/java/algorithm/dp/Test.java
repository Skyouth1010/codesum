package algorithm.dp;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		testRemove();
	}
	
	public static void testRemove() {
		int c = 39;
		int[] a = new int[40];// value for every one
		for (int i = 0; i < 40; i++) {
			a[i] = new Random().nextInt(100);
		}
		for (int k = 0; k < a.length; k++) {
			System.out.print(String.format("%-3s", a[k]));
		}
		System.out.println();
		System.out.println(c);
		int[] b = KnapsackProblem.remove(a, c);
		
		for (int k = 0; k < b.length; k++) {
			System.out.print(String.format("%-3s", b[k]));
		}
	}

}
