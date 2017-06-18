package algorithm.dp;

import java.util.Random;

/**
 * 01背包问题/快递小哥问题
 * 有n个包裹，每个包裹的重量为w(i)，价值为v(i)，挑选k个包裹，限定总重量为W，求价值最大化
 * 若s(1)...s(k)是问题的最优解，其各自重量为w(s(j))，价值为v(s(j))，去除s(j)后，剩余的包裹应该为W－w(s(j))的最优解
 * T(W)=max(v(j)+T(W－w(j))) 1<=j<=n
 * @author skyouth
 *
 * 2016年8月28日
 *
 */
public class KnapsackProblem {
	
	static int W,n;
	static int[] v,w;
	
	public static int knapsack(int n, int wight) {
		if (n < 0 || wight <= 0) return 0;
		
		int[][] c = new int[n+1][wight+1];
		
		for (int j = 0; j < wight+1; j++) {
			c[0][j] = 0;
		}
		int[][] selects = new int[n+1][wight+1];
		for (int i = 1; i < n+1; i++) {
			c[i][0] = 0;
			for (int j = 1; j < wight+1; j++) {
				if (w[i-1] <= j) {
					if (v[i-1]+c[i-1][j-w[i-1]]<=c[i-1][j]) {
						c[i][j] = c[i-1][j];
//						System.out.print(0+" ");
					} else {
						c[i][j] = v[i-1]+c[i-1][j-w[i-1]];
//						System.out.print(1+" ");
						selects[i][j] = 1;
					}
				} else {
//					System.out.print(0+" ");
					c[i][j] = c[i-1][j];
				}
			}
//			System.out.println();
		}
		
		int i=n,j=wight;
		while (true) {
			if (i==0 || j == 0) break;
			if (selects[i][j] == 1) {
				System.out.println(i + " selected");
				j = j - w[i-1];
			}
			i = i - 1;
		}
		System.out.println();
		
		
		return c[n][wight];
	}
	
	public static int knapsack1(int i, int wight) {
		// w are sorted array
		if (i < 0 || wight <= 0) return 0;
		
		if (w[i]>wight) return knapsack1(i-1, wight); 
		
		return Math.max(knapsack1(i-1, wight-w[i])+v[i], knapsack1(i-1, wight));
	}
	
	public static int knapsack2(int[] v, int[] w, int wight) {
		// w are sorted array
		if (v.length <= 0 || v.length != w.length) return 0;
//		boolean wighted = false;
//		for (int i = 0; i < w.length; i++) {
//			if (wight >= w[i]) {wighted = true;break;}
//		}
//		if (!wighted) return 0;
		
		int n = v.length;
		int maxv = 0;
		for (int i = 0; i < n; i++) {
			if (wight-w[i] > 0) {
				maxv = Math.max(maxv, v[i]+knapsack2(remove(v, i), remove(w, i), wight-w[i]));
			} else {
				maxv = Math.max(maxv, v[i]+knapsack2(remove(v, i), remove(w, i), wight));
			}
		}
		return maxv;
	}
	
	
	
	static int[] remove(int[] a, int i) {
		if (i < 0 || i >= a.length) return a;
		int[] b = new int[a.length - 1];
		System.arraycopy(a, 0, b, 0, i);
		System.arraycopy(a, i+1, b, i, a.length - i - 1);
		return b;
	}
	
	static {
		W = 100;// total wight
		n = 30;// knapsacks
		v = new int[n];// value for every one
		w = new int[n];// wight for every one
		for (int i = 0; i < n; i++) {
			v[i] = new Random().nextInt(100);
			w[i] = new Random().nextInt(W/(W/n));
		}
		int sumv = 0;
		for (int i = 0; i < n; i++) {
			sumv += v[i];
			System.out.print(String.format("%-4s", v[i]));
		}
		System.out.println(sumv);
		for (int i = 0; i < n; i++) {
			System.out.print(String.format("%-4s", w[i]));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println();
//		System.out.println(knapsack(v, w, W));
		long start = System.currentTimeMillis();
		System.out.println(knapsack1(n-1, W));
		long end = System.currentTimeMillis();
		System.out.println("time:" + (end-start));
		System.out.println(knapsack(n, W));
		System.out.println("time:" + (System.currentTimeMillis() - end));
	}

}
