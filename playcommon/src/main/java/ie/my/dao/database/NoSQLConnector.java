package ie.my.dao.database;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;

import ie.my.dao.Depot;
import ie.my.playcommon.JsonHelper;

import java.util.List;
import java.util.Set;

import org.bson.BSONObject;

import static java.util.concurrent.TimeUnit.SECONDS;

public class NoSQLConnector {
	MongoClient client = null;
	DB db = null;
	DBCollection col = null;
	
	public NoSQLConnector(String db,String host,int port){
		try{
			this.client = new MongoClient(host,port);
			this.db = client.getDB(db);
			this.col = this.db.getCollection(db);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public StringBuffer lookup(String key,String value){
		DBCursor cursor = this.col.find(new BasicDBObject(key, value));
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		try {
			for(int i = 0;cursor.hasNext();++i){
				DBObject obj = cursor.next();
				sb.append("{");
				sb.append(JsonHelper.appendValues("depot_name",(String) obj.get("depot_name")) );
				sb.append(JsonHelper.COMMA);
				
				sb.append(JsonHelper.appendValues("address",(String) obj.get("address")));
				sb.append(JsonHelper.COMMA);
				
				sb.append(JsonHelper.appendValues("telephone",(String) obj.get("telephone")));
				sb.append(JsonHelper.COMMA);
				
				BSONObject location= (BSONObject) obj.get("location");
				sb.append(JsonHelper.QUOTE+"location" +JsonHelper.QUOTE);
				sb.append(JsonHelper.COLON);
				sb.append(location);
				sb.append("}");
				if(i < cursor.count()-1)
					sb.append(JsonHelper.COMMA);
			}
		} finally {
			cursor.close();
		}
		sb.append("]");
		return sb;
	}
	
	
	/**
	 * we need to close the client to release the resource
	 */
	public void close(){
		try{
			this.client.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Helper
	 * @param obj
	 * @return
	 */
	public boolean insert(Object obj){
		boolean result = false;
		if(obj instanceof Depot){
			Depot depot = (Depot) obj;
			BasicDBObject doc = new BasicDBObject("city", depot.city);
			doc.append("depot_code", depot.depot_code);
			doc.append("depot_name", depot.depot_name);
			doc.append("address", depot.address);
			doc.append("telephone", depot.telephone);
			doc.append("location", new BasicDBObject("latitude",depot.location.latitude).append("longitude",depot.location.longitude) );
			
			this.col.insert(doc);
			result = true;
		}
		return result;
	}
	
	
	
	public static void main(String argv[]){
		NoSQLConnector connector = new NoSQLConnector("test","localhost",27017);
		StringBuffer buff = connector.lookup("city","3612");
		System.out.println(buff.toString());
	}
}
