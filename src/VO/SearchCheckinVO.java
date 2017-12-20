package VO;

import java.util.ArrayList;

public class SearchCheckinVO {
	
	
	public ArrayList<String> isbn = new ArrayList<String>();
	public ArrayList<String> title= new ArrayList<String>();
	public ArrayList<String> name= new ArrayList<String>();
	public ArrayList<String> checkoutBy= new ArrayList<String>();
	
	public ArrayList<String> getIsbn() {
		return isbn;
	}
	public void setIsbn(ArrayList<String> isbn) {
		this.isbn = isbn;
	}
	public ArrayList<String> getTitle() {
		return title;
	}
	public void setTitle(ArrayList<String> title) {
		this.title = title;
	}
	public ArrayList<String> getName() {
		return name;
	}
	public void setName(ArrayList<String> name) {
		this.name = name;
	}
	public ArrayList<String> getCheckoutBy() {
		return checkoutBy;
	}
	public void setCheckoutBy(ArrayList<String> checkoutBy) {
		this.checkoutBy = checkoutBy;
	}
	

}
