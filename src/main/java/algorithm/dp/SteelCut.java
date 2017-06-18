package algorithm.dp;

/**
 * 
 * 钢条切分问题
 * 给定一段长度为n英寸的钢条和一个价目表，求切割钢条方案使收益最大化。以下为不同长度的钢条和价格的对照表：
 * 长度 1 2 3 4 5  6  7  8  9  10
 * 价格 1 5 8 9 10 17 17 20 24 30
 * @author skyouth
 *
 */
public class SteelCut {

	/**
	 * 自顶向下计算长度为n的钢条的切割方案
	 * 递归式为r(n)=max(p(i),r(n-i))   (1<=i<=n)
	 * @param p 价格表
	 * @param n 钢条长度
	 * @param r 保存不同长度钢管的最优切割方案
	 */
	public static int memoizedCutRod(int[] p, int n, int[] r) {
		if (n == 1) return p[1]; 
		if(r[n] > 0) {
			return r[n];
		}
		int q = 0;
		for (int i = 1; i <= n;  i++) {
			q = Math.max(q, p[i]+memoizedCutRod(p, n-i, r));
		}
		
		r[n] = q;
		
		return q;
		
	}
	
	/**
	 * 自底向上计算长度为n的钢条的切割方案
	 * 每个子问题都依赖更小的子问题，如r(n)依赖r(n-1)...r(1)
	 * 递归式为r(n)=max(p(i),r(n-i))   (1<=i<=n)
	 * @param p 价格表
	 * @param n 钢条长度
	 * @param r 保存不同长度钢管的最优切割方案
	 */
	public static int bottomUpCutRod(int[] p, int n, int[] r) {
		int[] cuts = new int[n];
		if (n == 1) return p[1]; 
		int q = 0;
		for (int i = 1; i <= n;  i++) {
			int maxIndex = 1;
			for (int j=1; j <= i; j++) {
				int qmax = p[j]+r[i-j];
				if (qmax > q) {
					maxIndex = j;
					q = qmax;
				}
			}
			cuts[i-1] = maxIndex;
//			System.out.print(maxIndex + " ");
				
			r[i] = q;
		}
		int max = cuts[n-1];
		System.out.print(max+" ");
		while(true) {
			if (max >= n) break;
			int cut = cuts[n-max-1];
			System.out.print(cut);
			System.out.print(" ");
			max += cut;
		}
		
		return q;
	}

	public static void main(String[] args) {
		int[] p = new int[]{0,1,5,8,9,10,17,17,20,24,30};
		int[] r = new int[]{0,0,0,0,0,0,0,0,0,0,0};
		for (int i = 1; i<= 10; i++) {
			System.out.println(bottomUpCutRod(p, i, r));
		}
//		for (int i = 1; i<= 10; i++) {
//			int a = memoizedCutRod(p, i, r);
//			int b = bottomUpCutRod(p, i, r);
//			if (a != b) {
//				System.out.println("error");
//			}
//			
//			System.out.println(a);
//		}
	}
}
