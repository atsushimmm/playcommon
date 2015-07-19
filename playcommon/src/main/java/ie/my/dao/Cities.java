package ie.my.dao;

import ie.my.dao.database.Connector;

import java.sql.ResultSet;

public class Cities {

	
	private static String validationQuery(City c){
		return "select idx,country,city from cities where country='" + c.getCountry() + "' and city='"+ c.getCity() +"'";
	}
	
	/**
	 * SQL for insert city
	 * @param Country c
	 * @return String
	 */
	private static String insertQuery(City c){
		return "insert into cities (country,city) values ('" + c.getCountry() +"','"+c.getCity().replaceAll("'", " ")+"')";
	}
	
	public static boolean insertNew(Connector connector, String[] arr){
		boolean b = false;
		City c =  newInstance(arr);
		ResultSet rs = null;
		
		rs = connector.lookUp( validationQuery(c) );
		boolean hasRow=false;

		try{
			if ( rs.first() ){
				hasRow = true;
				/* 
				c.setIdx(rs.getString("idx"));
				c.setName(rs.getString("country_name"));
				if( c.getName()!=null ){
					list.put(c.getCountry(), c );
				}
				*/
			}
		}catch(Exception e){
			hasRow = false;
		}
		
		if(hasRow == true )
			return !hasRow;
		
		if( -1 < connector.executeUpdate(insertQuery(c)) ){
			rs = connector.lookUp( validationQuery(c) );
			try{
				if(rs.first()){
					
				}
			}catch(Exception e){
				
			}
		}
		
		return !hasRow;
	}
	
	private static City newInstance(String[] arr){
		
		String country=arr[3];
		String city = arr[24];
				
		country = country.substring(0, 2 );
		
		Country c = (Country) Countries.lookup(country);
		
		return new City ("", c.getIdx() ,  city   );
		
	}
	
	public static City lookup(Connector connector,String country, String city_name){
		City ci = null;
		try{
			ci = new City("",country,city_name);
			ResultSet rs = connector.lookUp(Cities.validationQuery(ci)) ;
			if(rs.first())
				ci.setIdx(rs.getString("idx"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return ci;
	}
}
