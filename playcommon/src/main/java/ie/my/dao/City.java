package ie.my.dao;

public class City {
	private String idx;
	private String country;
	private String city;
	
	public City(String idx,String country,String city){
		this.idx=idx;
		this.country=country;
		this.city=city;
	}
	
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getIdx() {
		return idx;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCity() {
		return city;
	}
	
}
