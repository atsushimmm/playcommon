package ie.my.playcommon;

public class JsonHelper {
	public static final String QUOTE="\"";
	public static final String COMMA=",";
	public static final String COLON=":";
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String appendValues(String key,String value){
		String buff="";
		buff += JsonHelper.QUOTE;
		buff += key;
		buff += JsonHelper.QUOTE;
		buff += JsonHelper.COLON;
		buff += JsonHelper.QUOTE;
		buff += value;
		buff += JsonHelper.QUOTE;
		return buff;
	}
	
	
}
