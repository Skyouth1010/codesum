package algorithm;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;

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
	
	public static void main(String[] args) {

	}


	@Override
	public Comparator<? super K> comparator() {
		return comparator;
	}


	@Override
	public K firstKey() {
		BinarySearchTreeNode<K, V> node = root;
		while (node != null) {
			node = node.left;
		}
		return keyOrNull(node);
	}


	@Override
	public K lastKey() {
		BinarySearchTreeNode<K, V> node = root;
		while (node != null) {
			node = node.right;
		}
		return keyOrNull(node);
	}


	@Override
	public BinarySearchTreeNode<K, V> lowerEntry(K key) {
		// TODO Auto-generated method stub
		BinarySearchTreeNode<K, V> node = root;
		
		while (node != null) {
			int cmp = compare(node.key, key);
			if (cmp < 0) {
				node = node.right;
				
			} else if (cmp > 0) {
				node = node.left;
				if (compare(node.key, key)<0) return node;
			} else {
				while (node != null) {
					if (node.right == null) {
						return node;
					}
					node = node.right;
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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public K floorKey(K key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map.Entry<K, V> ceilingEntry(K key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public K ceilingKey(K key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map.Entry<K, V> higherEntry(K key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public K higherKey(K key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map.Entry<K, V> firstEntry() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map.Entry<K, V> lastEntry() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map.Entry<K, V> pollFirstEntry() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Map.Entry<K, V> pollLastEntry() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public NavigableMap<K, V> descendingMap() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public NavigableSet<K> navigableKeySet() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public NavigableSet<K> descendingKeySet() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey,
			boolean toInclusive) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SortedMap<K, V> subMap(K fromKey, K toKey) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SortedMap<K, V> headMap(K toKey) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SortedMap<K, V> tailMap(K fromKey) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     * Return key for entry, or null if null
     */
    static <K,V> K keyOrNull(BinarySearchTreeNode<K,V> e) {
        return (e == null) ? null : e.key;
    }
    
    @SuppressWarnings("unchecked")
	final int compare(K k1, K k2) {
        return comparator==null ? ((Comparable<? super K>)k1).compareTo(k2)
            : comparator.compare(k1, k2);
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
	}
}
