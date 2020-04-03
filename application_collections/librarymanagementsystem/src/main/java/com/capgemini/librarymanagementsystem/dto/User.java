package com.capgemini.librarymanagementsystem.dto;

import java.util.Date;

import lombok.Data;

public class User {
	private int userId;
	private String name;
	private String email;
	private String password;
	private String department;
	private long mobileNumber;
	private Date issueDate;
	private Date returnDate;
	private int booksBorrowed;


	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public int getBooksBorrowed() {
		return booksBorrowed;
	}
	public void setBooksBorrowed(int booksBorrowed) {
		this.booksBorrowed = booksBorrowed;
	}


}//End of Main Method
