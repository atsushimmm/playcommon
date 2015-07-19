package ie.my.dao.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connector {
	public static String COMMA=",";
	
	private Connection conn;
	private Statement stmt;
	public Connector(){
	}
	
	
	public boolean Connect(String url,String user,String pass){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,pass);
			if(conn == null)
				return false;
			conn.setAutoCommit(true);
			stmt = conn.createStatement();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public ResultSet lookUp(String sql){
		ResultSet rs = null;
		try{
			rs = stmt.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public int executeUpdate(String sql){
		try{
			return stmt.executeUpdate(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
	public static String formatDate(String otaDate){
		return (otaDate == null || otaDate.trim().equals("")) ?"": otaDate.substring(0,10)+ " " + otaDate.substring(11);
	}
	
	public static String Quote(String val){
		return "'"+val+"'";
	}
	
	public void Close(){
		try{
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
