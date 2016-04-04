package concurrent.net.jcip.test;

public class Testdowhile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int i = 100;
		do {
			System.out.println(i);
			i++;
		} while (i < 10);
		i=100;
		while (i < 10) {
			System.out.println(i);
			i++;
		}
	}

}
