package test;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class TestJson {

	public static void main(String[] args) {
		
//		String msgStr = "{\"FileSize\":\"10455\",\"Url\":\"http://www.baidu.com?mid=1167196&resourceId=86429\",\"Template\":\"1\",\"MessageType\":\"13\",\"FileId\":\"A5BB061281E810DBCD30D4E84B314125_IMAGE\",\"Title\":\"Happy Day\",\"Summary\":\"Is Really A Happy Day33\"}";
		String msgStr = "{\"Text\":\"<a href=\"mailto:adsadsadadasdasdasdas1@#$%^&*()_+&%$#@#$%^&*()_dsadasdadsadasda\">adsadsadadasdasdasdas1@#$%^&*()_+&%$#@#$%^&*()_dsadasdadsadasda</a>@#￥%……&*（dadadadad#:]#:]#:]#:]\",\"MessageType\":\"-1\"}";

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, String> strModel = mapper.readValue(msgStr.replace("\n", "<br>"), HashMap.class);
			System.out.println(strModel);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
