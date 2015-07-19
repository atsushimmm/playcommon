package ie.my.dao;

public class Country {
	private String idx;
	private String country;
	private String name;
	
	public Country(String idx,String country){
		this.idx=idx;
		this.country = country;
	}
	
	public Country(String idx,String country,String name){
		this.idx=idx;
		this.country = country;
		this.name= name;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
