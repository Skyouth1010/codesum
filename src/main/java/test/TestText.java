package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestText {

	public static void main(String[] args) {
//		System.out.println(handleText("aaa    <br>http://mb.lenovomm.com/jump/?plat=lebrowser&area=%E5%90%8D%E7%AB%99&url=http://m.baidu.com/?from=1011384y<br> 分享来自浏览器！"));
		System.out.println(handleText("aaa aaa  aaaa   aaaa   aa"));
	}
	
	private static String handleText(String text) {
		StringBuilder sb = new StringBuilder();
		try {
			String[] potentialUrls = text.split(" ");
			
			String htmlRegex = "((http|https|ftp)\\://)?([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
			           + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"   
			           + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"   
			           + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"   
			           + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"   
			           + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"   
			           + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"   
			           + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
			
				Pattern p = Pattern.compile(htmlRegex);
				for (String s : potentialUrls) {
					String[] potentialUrls2 = s.split("<br>");
					for (String potentialUrl : potentialUrls2) {
						Matcher matcher = p.matcher(potentialUrl);
						if (matcher.find()) {
							sb.append("<a href='");
							sb.append(potentialUrl);
							sb.append("'>");
							sb.append(potentialUrl);
							sb.append("</a><br>");
						} else if (potentialUrls2.length > 1) {
							sb.append(potentialUrl);
							sb.append("<br>");
						} else {
							sb.append(potentialUrl);
						}
					}
				}
		} catch(Exception e) {
			return text;
		}
		
		return sb.toString();
	}
	
	private static String handleText2(String text) {
		StringBuilder sb = new StringBuilder();
		try {
			String htmlRegex = "((http|https|ftp)\\://)?([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
			           + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"   
			           + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"   
			           + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"   
			           + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"   
			           + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"   
			           + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"   
			           + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
			
				Pattern p = Pattern.compile(htmlRegex);

				Matcher matcher = p.matcher(text);
				if (matcher.find()) {
					sb.append("<a href='");
					sb.append(text);
					sb.append("'>");
					sb.append(text);
					sb.append("</a>");
				} else {
					sb.append(text);
				}
			
		} catch(Exception e) {
			return text;
		}
		
		return sb.toString();
	}
	
	private static String[] split(String text, String reg) {
		
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		
		String[] split1 = text.split(reg);
		if (split1 == null || split1.length == 0) {
			return new String[]{text};
		}
		for (int i = 0; i <= split1.length - 1; i++ ) {
			
		}
		
		return new String[0];
	}

}
