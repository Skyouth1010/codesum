package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestArrayList {

	public static void main(String[] args) {
		
		List<String> a = Arrays.asList(new String[]{"a","b","c"});
		System.out.println(a);
		//a.add("d");// 不能直接添加
		List<String> b = new ArrayList<String>(a);
		b.add("d");
		System.out.println(b);
		
		Set<String> userids = new HashSet<String>();
		userids.add("a");
		userids.add("b");
		System.out.println(Arrays.toString(userids.toArray()));
		
		Date date = new Date(1458294586301L);
		System.out.println(date);
		
		
		
		Calendar c = Calendar.getInstance();
		System.out.println(c.getTimeZone());
	}
}
