package concurrent.net.jcip.test;

import java.util.Scanner;

public class TestKeyboard {

	public static void main(String[] args) {
		while(true) {
			Scanner input = new Scanner(System.in);
			String s = input.nextLine();
			System.out.println("输入内容为:" + s);
		}
	}

}
