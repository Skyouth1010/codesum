package algorithm.datastructure;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;

/**
 * 二叉搜索树实现的Map，由于树不平衡，最坏情况下性能较差
 * 红黑树即是在此树基础上进行再平衡生成。
 * @author skyouth
 *
 * 2016年9月5日
 *
 * @param <K>
 * @param <V>
 */
public class BinarySearchTreeMap<K, V> 
	extends AbstractMap<K,V>
	implements NavigableMap<K,V>, Cloneable, java.io.Serializable{
	
	private static final long serialVersionUID = -3386524470791528471L;
	
	private transient BinarySearchTreeNode<K, V> root = null;
	
	private transient int size = 0;
	
	private final Comparator<? super K> comparator;
	
	public BinarySearchTreeMap() {
		comparator = null;
	}
	
	public BinarySearchTreeMap(Comparator<? super K> comparator) {
        this.comparator = comparator;
    }
	
	public BinarySearchTreeMap(Map<? extends K, ? extends V> m) {
        comparator = null;
        putAll(m);
    }
	
	@Override
	public Comparator<? super K> comparator() {
		return comparator;
	}


	@Override
	public K firstKey() {
		BinarySearchTreeNode<K, V> node = root;
		if (node != null) {
			while (node.left != null) {
				node = node.left;
			}
		}
		return keyOrNull(node);
	}


	@Override
	public K lastKey() {
		BinarySearchTreeNode<K, V> node = root;
		if (node != null) {
			while (node.right != null) {
				node = node.right;
			}
		}
		return keyOrNull(node);
	}


	@Override
	public Map.Entry<K, V> lowerEntry(K key) {
		BinarySearchTreeNode<K, V> node = root;
		
		while (node != null) {
			int cmp = compare(key, node.key);
			if (cmp > 0) {
				if (node.right != null) {
					node = node.right;
				} else return node;// 右孩子不存在，则本节点就是小于key的最大节点了
			} else {
				if (node.left != null) {
					node = node.left;
				} else {
					BinarySearchTreeNode<K, V> parent = node.parent;
					BinarySearchTreeNode<K, V> current = node;
					while (parent != null && current == parent.left) {
						current = parent;
						parent = parent.parent;
					}
					return parent;
				}
			}
		}
		return null;
	}


	@Override
	public K lowerKey(K key) {
		return keyOrNull(lowerEntry(key));
	}


	@Override
	public Map.Entry<K, V> floorEntry(K key) {
		BinarySearchTreeNode<K, V> node = root;
		
		while (node != null) {
			int cmp = compare(key, node.key);
			if (cmp > 0) {
				if (node.right != null) {
					node = node.right;
				} else return node;// 右孩子不存在，则本节点就是小于key的最大节点了
			} else if (cmp < 0) {
				if (node.left != null) {
					node = node.left;
				} else {
					BinarySearchTreeNode<K, V> parent = node.parent;
					BinarySearchTreeNode<K, V> current = node;
					while (parent != null && current == parent.left) {
						current = parent;
						parent = parent.parent;
					}
					return parent;
				}
			} else return node;
		}
		return null;
	}


	@Override
	public K floorKey(K key) {
		return keyOrNull(floorEntry(key));
	}


	@Override
	public Map.Entry<K, V> ceilingEntry(K key) {
		// 和floorEntry是对称的
		BinarySearchTreeNode<K, V> node = root;
		
		while (node != null) {
			int cmp = compare(key, node.key);
			if (cmp < 0) {
				if (node.left != null) {
					node = node.left;
				} else return node;
			} else if (cmp > 0) {
				if (node.right != null) {
					node = node.right;
				} else {
					BinarySearchTreeNode<K, V> parent = node.parent;
					BinarySearchTreeNode<K, V> current = node;
					while (parent != null && current == parent.right) {
						current = parent;
						parent = parent.parent;
					}
					return parent;
				}
			} else return node;
		}
		return null;
	}


	@Override
	public K ceilingKey(K key) {
		return keyOrNull(ceilingEntry(key));
	}


	@Override
	public Map.Entry<K, V> higherEntry(K key) {
		// 和lowerEntry是对称的
		BinarySearchTreeNode<K, V> node = root;
		
		while (node != null) {
			int cmp = compare(key, node.key);
			if (cmp < 0) {
				if (node.left != null) {
					node = node.left;
				} else return node;
			} else {
				if (node.right != null) {
					node = node.right;
				} else {
					BinarySearchTreeNode<K, V> parent = node.parent;
					BinarySearchTreeNode<K, V> current = node;
					while (parent != null && current == parent.right) {
						current = parent;
						parent = parent.parent;
					}
					return parent;
				}
			}
		}
		return null;
	}


	@Override
	public K higherKey(K key) {
		return keyOrNull(higherEntry(key));
	}


	@Override
	public Map.Entry<K, V> firstEntry() {
		return exportEntry(getFirstEntry());
	}
	
	public BinarySearchTreeNode<K, V> getFirstEntry() {
		BinarySearchTreeNode<K, V> node = root;
		while (node != null) {
			node = node.left;
		}
		return node;
	}


	@Override
	public Map.Entry<K, V> lastEntry() {
		return exportEntry(getLastEntry());
	}
	
	final BinarySearchTreeNode<K, V> getLastEntry() {
		BinarySearchTreeNode<K, V> node = root;
		while (node != null) {
			node = node.right;
		}
		return node;
	}


	@Override
	public Map.Entry<K, V> pollFirstEntry() {
		BinarySearchTreeNode<K, V> node = getFirstEntry();
		if (node != null) {
			BinarySearchTreeNode<K, V> parent = node.parent;
			if (parent != null) {
				parent.left = null;
			}
			node.parent = null;
		}
		return exportEntry(node);
	}


	@Override
	public Map.Entry<K, V> pollLastEntry() {
		BinarySearchTreeNode<K, V> node = getLastEntry();
		if (node != null) {
			BinarySearchTreeNode<K, V> parent = node.parent;
			if (parent != null) {
				parent.right = null;
			}
			node.parent = null;
		}
		return exportEntry(node);
	}

	
	/************************** TODO below methods refer to treeMap *****************/

	@Override
	public NavigableMap<K, V> descendingMap() {
		return null;
	}


	@Override
	public NavigableSet<K> navigableKeySet() {
		return null;
	}


	@Override
	public NavigableSet<K> descendingKeySet() {
		return descendingMap().navigableKeySet();
	}


	@Override
	public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey,
			boolean toInclusive) {
		return null;
	}


	@Override
	public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
		return null;
	}


	@Override
	public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
		return null;
	}


	@Override
	public SortedMap<K, V> subMap(K fromKey, K toKey) {
		return null;
	}


	@Override
	public SortedMap<K, V> headMap(K toKey) {
		return null;
	}


	@Override
	public SortedMap<K, V> tailMap(K fromKey) {
		return null;
	}
	
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return null;
	}
	
	/************************** TODO above methods refer to treeMap *****************/
	
	
	@Override
	public V put(K key, V value) {
		BinarySearchTreeNode<K,V> t = root;
		if (t == null) {
			compare(key, key); // type (and possibly null) check
			root = new BinarySearchTreeNode<>(key, value, null);
			size = 1;
			return null;
		}
		
		BinarySearchTreeNode<K,V> parent = t;
		int cmp = 0;
		while (t != null) {
			parent = t;
			cmp = compare(key, t.key);
			if (cmp > 0) {
				t = t.right;
			} else if (cmp < 0) {
				t = t.left;
			} else {
				return t.setValue(value);
			}
		}

		BinarySearchTreeNode<K,V> node = new BinarySearchTreeNode<>(key, value, parent);
		if(cmp > 0) parent.right = node;
		else parent.left = node;
		size++;
		
		return null;
	}


	/**
     * Return key for entry, or null if null
     */
    static <K,V> K keyOrNull(Map.Entry<K,V> e) {
        return (e == null) ? null : e.getKey();
    }
    
    @SuppressWarnings("unchecked")
	final int compare(K k1, K k2) {
        return comparator==null ? ((Comparable<? super K>)k1).compareTo(k2)
            : comparator.compare(k1, k2);
    }
    
    /**
     * Return SimpleImmutableEntry for entry, or null if null
     */
    static <K,V> Map.Entry<K,V> exportEntry(BinarySearchTreeNode<K,V> e) {
        return (e == null) ? null :
            new AbstractMap.SimpleImmutableEntry<>(e);
    }
    
    static class BinarySearchTreeNode<K, V> implements Map.Entry<K,V> {
		K key;
		V value;
		BinarySearchTreeNode<K, V> left;
		BinarySearchTreeNode<K, V> right;
		BinarySearchTreeNode<K, V> parent;
		
		BinarySearchTreeNode(K key, V value, BinarySearchTreeNode<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
		
		@Override
		public K getKey() {
			return key;
		}
		@Override
		public V getValue() {
			return value;
		}
		@Override
		public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((left == null) ? 0 : left.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Map.Entry) || obj == null) {
				return false;
			}
			Map.Entry<?,?> o = (Map.Entry<?,?>) obj;
			if (key.equals(o.getKey()) && value.equals(o.getValue())) {
				return true;
			}
			return false;
		}
	}
}
