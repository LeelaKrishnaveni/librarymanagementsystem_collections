package com.capgemini.librarymanagementsystem_jdbc.dto;

import java.io.Serializable;

public class BookDetails implements Serializable{
	private int bId;
	private String bookName;
	private String authorName;
	private String category;
	private String publisher;
	//private int copies;
	public int getBId() {
		return bId;
	}
	public void setBId(int bId) {
		this.bId = bId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/*
	 * public int getCopies() { return copies; } public void setCopies(int copies) {
	 * this.copies = copies; }
	 */
}

