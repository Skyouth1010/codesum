package languagefeature;

import java.util.Arrays;

public class TestReference {

	private volatile transient Object[] array;
	
    private Object get(Object[] a, int index) {
        return a[index];
    }

    public Object get(int index) {
        return get(getArray(), index);
    }
    
    public boolean add(Object e) {
        Object[] elements = getArray();
        int len = elements.length;
        Object[] newElements = Arrays.copyOf(elements, len + 1);
        newElements[len] = e;
        setArray(newElements);
        return true;
    }
    
    final Object[] getArray() {
        return array;
    }
	
	final void setArray(Object[] a) {
        array = a;
    }
	
	class A extends Thread {
		public void run() {
			
		}
	}
	
	class B extends Thread {
		public void run() {
			
		}
	}
	
	public static void main(String[] args) {
		TestReference tr = new TestReference();
		tr.setArray(new String[]{"a","b"});
		System.out.println(tr.get(1));
		tr.setArray(new String[]{"a","b","c"});
		System.out.println(tr.get(2));
	}
}
