package model;

public class ItemBean {

	private int catID;
	private String number;
	private String name;
	private double price;
	
	
	public ItemBean(int catID, String number, String name, double price) {
		super();
		this.catID = catID;
		this.number = number;
		this.name = name;
		this.price = price;
	}
	
	public ItemBean() {}
	
	public int getCatID() {
		return catID;
	}
	public void setCatID(int catID) {
		this.catID = catID;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public boolean equals(Object obj) 
    {    
		if(this == obj) return true;      
        
		if(obj == null || obj.getClass()!= this.getClass()) return false; 
          
        ItemBean geek = (ItemBean) obj; 
        
        return (this.catID == geek.catID && 
        		this.name == geek.name &&
        		this.number == geek.number && 
        		this.price == geek.price); 
    } 

	@Override
	public String toString() {
		return "ItemBean [getCatID()=" + getCatID() + ", getNumber()=" + getNumber() + ", getName()=" + getName()
				+ ", getPrice()=" + getPrice() + "]";
	}
	
	
	
}
