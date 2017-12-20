package VO;

import java.util.ArrayList;
import java.util.Date;

public class FineTableVO {
	
	public ArrayList<String> getLoanID() {
		return loanID;
	}
	public void setLoanID(ArrayList<String> loanID) {
		this.loanID = loanID;
	}
	public ArrayList<String> getPaid() {
		return paid;
	}
	public void setPaid(ArrayList<String> paid) {
		this.paid = paid;
	}
	public ArrayList<Float> getFineamt() {
		return fineamt;
	}
	public void setFineamt(ArrayList<Float> fineamt) {
		this.fineamt = fineamt;
	}
	public ArrayList<String> getIsbn() {
		return isbn;
	}
	public void setIsbn(ArrayList<String> isbn) {
		this.isbn = isbn;
	}
	public ArrayList<String> getBorrower() {
		return borrower;
	}
	public void setBorrower(ArrayList<String> borrower) {
		this.borrower = borrower;
	}
	public ArrayList<String> loanID = new ArrayList<String>();
	public ArrayList<String> paid = new ArrayList<String>();
	public ArrayList<Float> fineamt = new ArrayList<Float>();
	public ArrayList<String> isbn = new ArrayList<String>();
	public ArrayList<String> borrower = new ArrayList<String>();
	public ArrayList<String> datein = new ArrayList<String>();
	public ArrayList<Integer> cardid = new ArrayList<Integer>();

	public ArrayList<Integer> getCardid() {
		return cardid;
	}
	public void setCardid(ArrayList<Integer> cardid) {
		this.cardid = cardid;
	}
	public ArrayList<String> getDatein() {
		return datein;
	}
	public void setDatein(ArrayList<String> datein) {
		this.datein = datein;
	}
	
	

	
}
