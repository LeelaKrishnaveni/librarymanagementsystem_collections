package com.capgemini.librarymanagementsystem.service;

import java.util.LinkedList;

import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.Request;
import com.capgemini.librarymanagementsystem.dto.User;

public interface UserService {
	boolean registerUser(User user);
	User loginUser(String email,String password);
	public Request bookRequest(User user, BookDetails book);
	public Request bookReturn(User user, BookDetails book);
	//Book borrowBook(int id);
	LinkedList<BookDetails> searchBookByTitle(String bookName);
	LinkedList<BookDetails> searchBookByAuthor(String authorName);
	LinkedList<BookDetails> searchBookByCategory(String category);
	LinkedList<BookDetails> getBooksInfo();
}
