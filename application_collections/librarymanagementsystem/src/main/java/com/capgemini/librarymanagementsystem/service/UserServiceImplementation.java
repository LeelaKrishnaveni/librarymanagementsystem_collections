package com.capgemini.librarymanagementsystem.service;

import java.util.LinkedList;

import com.capgemini.librarymanagementsystem.dao.UserDAO;
import com.capgemini.librarymanagementsystem.db.LibraryDB;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.Request;
import com.capgemini.librarymanagementsystem.dto.User;
import com.capgemini.librarymanagementsystem.factory.LibraryFactory;

public class UserServiceImplementation implements UserService{

	private UserDAO dao = LibraryFactory.getUserDao();

	@Override
	public boolean registerUser(User user) {
		return dao.registerUser(user);
	}

	@Override
	public User loginUser(String email, String password) {
		return dao.loginUser(email, password);
	}

	@Override
	public Request bookRequest(User user, BookDetails book) {
		return dao.bookRequest(user, book);
	}

	@Override
	public Request bookReturn(User user, BookDetails book) {
		return dao.bookReturn(user, book);
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
	public LinkedList<BookDetails> searchBookByCategory(String category) {
		return dao.searchBookByCategory(category);
	}

	@Override
	public LinkedList<BookDetails> getBooksInfo() {
		return null;
	}

}
