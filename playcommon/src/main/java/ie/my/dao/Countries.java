package ie.my.dao;

import ie.my.dao.database.Connector;

import java.sql.ResultSet;
import java.util.HashMap;

public class Countries {
	private static HashMap<String,Country> list = new HashMap<String,Country>();

	/**
	 * SQL for duplicates validation 
	 * @param Country c
	 * @return String 
	 */
	private static String validationQuery(Country c){
		return "select idx,country,country_name from countries where country='" + c.getCountry() + "'";
	}
	
	/**
	 * SQL for insert country
	 * @param Country c
	 * @return String
	 */
	private static String insertQuery(Country c){
		return "insert into countries (country) values ('" + c.getCountry() + "')";
	}

	/**
	 * insert new Country into country table;
	 * @param connector
	 * @param arr
	 * @return boolean
	 */
	public static boolean insertNew(Connector connector, String[] arr){
		boolean b = false;
		Country c =  newInstance(arr);
		ResultSet rs = null;
		
		rs = connector.lookUp( validationQuery(c) );
		boolean hasCountry=false;

		try{
			if ( rs.first() ){
				hasCountry = true;
				c.setIdx(rs.getString("idx"));
				c.setName(rs.getString("country_name"));
				if( c.getName()!=null ){
					list.put(c.getCountry(), c );
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			hasCountry = false;
		}
		
		if(hasCountry == true )
			return !hasCountry;
		
		if( -1 < connector.executeUpdate(insertQuery(c)) ){
			rs = connector.lookUp( validationQuery(c) );
			try{
				if(rs.first()){
					c.setIdx(rs.getString("idx"));
					c.setName(rs.getString("country_name"));
					if( c.getName()!=null ){
						list.put(c.getCountry(), c );
					}	
				}
			}catch(Exception e){
				
			}
		}
		
		return !hasCountry;
	}
	
	private static Country newInstance(String[] arr){
		
		String country=arr[3];
		
		country = country.substring(0, 2 );
		
		return new Country("",country);
		
	}
	
	public static Country getCountryBy(String country){
		return list.get(country);
	}
	
	public static void lookupAll(Connector connector){
		ResultSet rs =null;
		try{
			rs = connector.lookUp("select * from countries");
			if( rs.first() ){
				do{
					Country c = new Country(rs.getString("idx"),rs.getString("country"),rs.getString("country_name") );
					list.put(c.getCountry(), c );
				}while(rs.next());
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	public static Country lookup(String country){
		return (Country) list.get(country);
	}
}
