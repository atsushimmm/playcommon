package ie.my.playcommon;

import ie.my.dao.Cities;
import ie.my.dao.City;
import ie.my.dao.Country;
import ie.my.dao.Depot;
import ie.my.dao.Location;
import ie.my.dao.database.Connector;
import ie.my.dao.database.NoSQLConnector;
import ie.my.dao.Countries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class Application3 {
	
	public static void main(String[] args) {
		try{
			NoSQLConnector nosqlconnector = new NoSQLConnector("test", "localhost", 27017);
			
			Connector connector = new Connector();
			connector.Connect("jdbc:mysql://localhost:3306/test", "atsushi", "abcdef1");
			// map countries into the memory
			Countries.lookupAll(connector);
			File f = new File("/home/atsushi/tmp/Locs.dat");
			
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String tmp;
			while( (tmp=br.readLine())!=null ){
				String [] arr = tmp.split("\t");
				//Countries.insertNew(connector, arr);
				//Cities.insertNew(connector, arr);
				for(int i = 0;i<arr.length; ++i){
					System.out.println(i+" "+arr[i]);
				}
				String country= ((Country) Countries.lookup(arr[3].substring(0,2))).getIdx();

				String city=arr[24];

				City ci = Cities.lookup(connector, country, city );
				if(ci != null){
					Depot depot = new Depot();
					depot.city    = ci.getIdx();
					depot.depot_code = arr[3].substring(0,2)+"-"+arr[1];
					depot.depot_name = arr[2].trim();
					depot.address = arr[23].trim();
					depot.telephone = arr[8].trim();
					depot.location = new Location(arr[6],arr[7]);
					nosqlconnector.insert(depot);
				}
			}
			br.close();
			fr.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
