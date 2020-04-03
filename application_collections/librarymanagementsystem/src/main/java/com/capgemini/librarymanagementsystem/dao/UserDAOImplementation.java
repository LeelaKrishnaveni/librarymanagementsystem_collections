package com.capgemini.librarymanagementsystem.dao;

import java.awt.print.Book;
import java.util.LinkedList;

import com.capgemini.librarymanagementsystem.db.LibraryDB;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.Request;
import com.capgemini.librarymanagementsystem.dto.User;
import com.capgemini.librarymanagementsystem.exception.LibraryManagementSystemException;

public class UserDAOImplementation implements UserDAO {

	@Override
	public boolean registerUser(User user) {
		for(User u : LibraryDB.USER) {
			if(u.getEmail().equals(user.getEmail())) {
				return false;
			}
		}
		LibraryDB.USER.add(user);
		return true;
	}

	@Override
	public User loginUser(String email, String password) {
		for(User user : LibraryDB.USER) {
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		throw new LibraryManagementSystemException("Invalid Credentials");	
	}

	@Override
	public Request bookRequest(User user, BookDetails book) {
		boolean flag = false, 
				isRequestExists = false;
		Request requestInfo = new Request();
		User userInfo2 = new User();
		BookDetails bookInfo2 = new BookDetails();

		for (Request requestInfo2 : LibraryDB.REQUEST) {
			if (book.getBookId() == requestInfo2.getBookInfo().getBookId()) {
				isRequestExists = true;

			}

		}

		if (!isRequestExists) {
			for (User userBean : LibraryDB.USER) {
				if (user.getUserId() == userBean.getUserId()) {
					for (BookDetails book1 : LibraryDB.BOOK) {
						if (book1.getBookId() == book1.getBookId()) {
							userInfo2 = userBean;
							bookInfo2 = book1;
							flag = true;
						}
					}
				}
			}
			if (flag == true) {
				requestInfo.setBookInfo(bookInfo2);
				requestInfo.setUserInfo(userInfo2);;
				LibraryDB.REQUEST.add(requestInfo);
				return requestInfo;
			}

		}

		throw new LibraryManagementSystemException("Invalid request or you cannot request that book");
	}

	@Override
	public Request bookReturn(User user, BookDetails book) {
		for (Request requestInfo : LibraryDB.REQUEST) {

			if (requestInfo.getBookInfo().getBookId() == book.getBookId() &&
					requestInfo.getUserInfo().getUserId() == user.getUserId() &&
					requestInfo.isIssued() == true) {


				System.out.println("Returning Issued book only");
				requestInfo.setReturned(true);


				return requestInfo;
			}

		}

		throw new  LibraryManagementSystemException("Invalid return ");
	}

	@Override
	public LinkedList<BookDetails> searchBookByTitle(String bookName) {
		LinkedList<BookDetails> searchList=new LinkedList<BookDetails>();
		for(int i=0;i<=LibraryDB.BOOK.size()-1;i++)
		{
			BookDetails retrievedBook=LibraryDB.BOOK.get(i);
			String retrievedBookName=retrievedBook.getBookName();
			if(bookName.equals(retrievedBookName))
			{
				searchList.add(retrievedBook);	
				return searchList;
			}
		}
		if(searchList.size()==0)
		{
			throw new LibraryManagementSystemException ("Book is not found");
		}
		else
		{
			return searchList;
		}

	}

	@Override
	public LinkedList<BookDetails> searchBookByAuthor(String authorName) {
		LinkedList<BookDetails> searchList=new LinkedList<BookDetails>();
		for(int i=0;i<=LibraryDB.BOOK.size()-1;i++)
		{
			BookDetails retrievedBook=LibraryDB.BOOK.get(i);
			String retrievedBookAuthor=retrievedBook.getAuthorName();
			if(authorName.equals(retrievedBookAuthor))
			{
				searchList.add(retrievedBook);	
			}
		}
		if(searchList.size()==0)
		{
			throw new LibraryManagementSystemException ("Book is not found");
		}
		else
		{
			return searchList;
		}	

	}

	@Override
	public LinkedList<BookDetails> searchBookByCategory(String category) {
		LinkedList<BookDetails> searchList=new LinkedList<BookDetails>();
		for(int i=0;i<=LibraryDB.BOOK.size()-1;i++)
		{
			BookDetails retrievedBook=LibraryDB.BOOK.get(i);
			String retrievedCategory=retrievedBook.getCategory();
			if(category.equals(retrievedCategory))
			{
				searchList.add(retrievedBook);	
			}
		}
		if(searchList.size()==0)
		{
			throw new LibraryManagementSystemException("Book not found");
		}
		else
		{
			return searchList;
		}	


	}

	@Override
	public LinkedList<BookDetails> getBooksInfo() {
		return LibraryDB.BOOK;
	}

}