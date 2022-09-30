package com.bean;

public class Transactions {
private String accno;
private int debit;
private int credit;
private int avail_bal;

public Transactions(String accno, int debit, int credit, int avail_bal) {
	super();
	this.accno = accno;
	this.debit = debit;
	this.credit = credit;
	this.avail_bal = avail_bal;
}
public String getAccno() {
	return accno;
}
public void setAccno(String accno) {
	this.accno = accno;
}
public int getDebit() {
	return debit;
}
public void setDebit(int debit) {
	this.debit = debit;
}
public int getCredit() {
	return credit;
}
public void setCredit(int credit) {
	this.credit = credit;
}
public int getAvail_bal() {
	return avail_bal;
}
public void setAvail_bal(int avail_bal) {
	this.avail_bal = avail_bal;
}
@Override
public String toString() {
	return "Transactions [accno=" + accno + ", debit=" + debit + ", credit=" + credit + ", avail_bal=" + avail_bal
			+ "]";
}

}
