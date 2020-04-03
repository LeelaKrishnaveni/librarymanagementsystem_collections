package com.capgemini.librarymanagementsystem.service;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.db.LibraryDB;
import com.capgemini.librarymanagementsystem.dto.Admin;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.Request;
import com.capgemini.librarymanagementsystem.dto.User;
import com.capgemini.librarymanagementsystem.factory.LibraryFactory;

public class AdminServiceImplementation implements AdminService{
	private AdminDAO dao = LibraryFactory.getAdminDao();

	@Override
	public boolean registerAdmin(Admin admin) {
		return dao.registerAdmin(admin);

	}

	@Override
	public Admin loginAdmin(String email, String password) {
		return dao.loginAdmin(email, password);
	}

	@Override
	public boolean addBook(BookDetails book) {
		return dao.addBook(book);
	}

	@Override
	public boolean removeBook(int id) {
		return dao.removeBook(id);
	}

	@Override
	public int updateBook(int AdminId) {
		return dao.updateBook(AdminId);
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
		return dao.getBooksInfo();
	}

	@Override
	public List<User> showUsers() {
		return dao.showUsers();
	}

	@Override
	public List<Request> showRequests() {
		return dao.showRequests();
	}

	@Override
	public boolean bookIssue(User user, BookDetails bookDetails) {
		return dao.bookIssue(user, bookDetails);
	}

	@Override
	public boolean isBookReceived(User user, BookDetails bookDetails) {
		return dao.isBookReceived(user, bookDetails);
	}

}
