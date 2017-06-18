package algorithm.sort;

/**
 * 
 * 排序算法接口
 * 算法	最坏情况运行时间	平均情况/期望运行时间
 * 插入排序 
 * 归并排序
 * 堆排序
 * 快速排序
 * 计数排序
 * 基数排序
 * 桶排序
 * 
 * 其中，前四种为比较排序，归并排序是非原址的，插入、堆、快速排序是原址的；
 * 使用决策树模型，可以证明堆排序和归并排序是渐近最优的比较排序算法。
 * @author skyouth
 *
 */
public interface SortStrategy<T> {

	public void sort(Element<T>[] elements);

}
