package com.capgemini.librarymanagementsystem_jdbc.dao;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystem_jdbc.dto.BookDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.BookIssueDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.BooksBorrowed;
import com.capgemini.librarymanagementsystem_jdbc.dto.RequestDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.UserDetails;

public interface UserDetailsDAO {

	boolean register(UserDetails user);
	UserDetails login(String email,String password);
	boolean addBook(BookDetails book);
	boolean removeBook(int bId);
	boolean updateBook(BookDetails book);
	boolean issueBook(int bId,int uId);
	boolean request(int uId, int bId);
	List<BooksBorrowed> bookBorrowed(int uId);
	LinkedList<BookDetails> searchBookById(int bId);
	LinkedList<BookDetails> searchBookByTitle(String bookName);
	LinkedList<BookDetails> searchBookByAuthor(String authorName);
	LinkedList<BookDetails> getBooksInfo();
	boolean returnBook(int bId,int uId,String status);
	LinkedList<RequestDetails> showRequests();
	LinkedList<BookIssueDetails> bookHistoryDetails(int uId);
	LinkedList<UserDetails> showUsers();
	boolean updatePassword(String email,String password,String newPassword,String role);
	LinkedList<BookIssueDetails> showIssuedBooks();

}


