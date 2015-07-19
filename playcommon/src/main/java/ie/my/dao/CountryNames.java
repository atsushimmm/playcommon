package ie.my.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import ie.my.dao.database.Connector;

public class CountryNames {
	public static void readFile(ie.my.dao.database.Connector connector){
		File f = new File("/home/atsushi/tmp/countrycodes.csv");
		try{
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String tmp;
			while( (tmp=br.readLine())!=null ){
				String [] arr = tmp.split(",");
				//Countries.insertNew(connector, arr);
				connector.executeUpdate("update countries set country_name='"+arr[1]+"' where country='"+arr[0]+"'");
			}
			br.close();
			fr.close();
		}catch(Exception e){
			
		}
	}
	public static void main(String argv[]){
		try{
			Connector connector = new Connector();
			connector.Connect("jdbc:mysql://localhost:3306/test", "atsushi", "abcdef1");
			readFile(connector);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
