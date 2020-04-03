package com.capgemini.librarymanagementsystem_jdbc.service;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystem_jdbc.dao.UserDetailsDAO;
import com.capgemini.librarymanagementsystem_jdbc.dto.BookDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.BookIssueDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.BooksBorrowed;
import com.capgemini.librarymanagementsystem_jdbc.dto.RequestDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.UserDetails;
import com.capgemini.librarymanagementsystem_jdbc.factory.LibraryFactory;

public class UserServiceImplementation implements UserService{
	private UserDetailsDAO dao=LibraryFactory.getUserDAO();

	@Override
	public boolean register(UserDetails user) {
		return dao.register(user);
	}

	@Override
	public UserDetails login(String email, String password) {
		return dao.login(email, password);
	}

	@Override
	public boolean addBook(BookDetails book) {
		return dao.addBook(book);
	}

	@Override
	public boolean removeBook(int bId) {
		return dao.removeBook(bId);
	}

	@Override
	public boolean updateBook(BookDetails book) {
		return dao.updateBook(book);
	}

	@Override
	public boolean issueBook(int bId, int uId) {
		return dao.issueBook(bId, uId);
	}

	@Override
	public boolean request(int uId, int bId) {
		return dao.request(uId, bId);
	}

	@Override
	public List<BooksBorrowed> bookBorrowed(int uId) {
		return dao.bookBorrowed(uId);
	}

	@Override
	public LinkedList<BookDetails> searchBookById(int bId) {
		return dao.searchBookById(bId);
	}

	@Override
	public LinkedList<BookDetails> searchBookByTitle(String bookName) {
		return dao.searchBookByTitle(bookName);
	}

	@Override
	public LinkedList<BookDetails> searchBookByAuthor(String authorName) {
		return dao.searchBookByAuthor(authorName);
	}

	@Override
	public LinkedList<BookDetails> getBooksInfo() {
		return dao.getBooksInfo();
	}

	@Override
	public boolean returnBook(int bId, int uId,String status) {
		return dao.returnBook(bId, uId, status);
	}

	@Override
	public LinkedList<BookIssueDetails> bookHistoryDetails(int uId) {
		return dao.bookHistoryDetails(uId);
	}

	@Override
	public LinkedList<RequestDetails> showRequests() {
		return dao.showRequests();
	}

	@Override
	public LinkedList<UserDetails> showUsers() {
		return dao.showUsers();
	}

	@Override
	public LinkedList<BookIssueDetails> showIssuedBooks() {
		return dao.showIssuedBooks();
	}

	@Override
	public boolean updatePassword(String email, String password, String newPassword, String role) {
		return dao.updatePassword(email, password, newPassword, role);
	}


}
