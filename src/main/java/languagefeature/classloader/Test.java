package languagefeature.classloader;

public class Test {

	
	static int aa;
	
	static {
		aa = 11;
		System.out.println("aa inited: " +aa);
	}
}
