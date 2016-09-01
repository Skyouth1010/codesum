/**
 * 动态规划算法分析和实现
 * 动态规划是用来解决最优化问题的高效算法，通常分几个步骤：
 * 首先要找到最优子结构，将问题分解为一个或多个子问题，然后再构造出原问题的解。而子问题存在重叠。
 * 根据以上子问题的拆解可以构造递归式，使用递归式，通过自顶向下或自底向上的方法构造原问题的解。
 * 
 * 其实Fibonacci函数是一个最简单的动态规划问题，如果使用普通的递归方式f(n)=f(n-1)+f(n-2)，效率会很低，
 * 因为这里存在大量的重复计算（如f(n-1)＝f(n-2)＋f(n-3)，f(n-2)就和前面公式重复计算了）。因此高效率的算法在于
 * 去除重复计算，采用自底向上的方式计算，先计算f(0),f(1)...f(n)往上计算的方式，消除重复计算，大大减少重复计算。
 * 其他经典问题如钢管切割问题 0-1背包问题等等。
 * 
 * @author skyouth
 *
 */
package algorithm.dp;