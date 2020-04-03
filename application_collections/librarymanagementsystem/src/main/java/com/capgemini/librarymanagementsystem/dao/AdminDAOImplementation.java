package com.capgemini.librarymanagementsystem.dao;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystem.db.LibraryDB;
import com.capgemini.librarymanagementsystem.dto.Admin;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.Request;
import com.capgemini.librarymanagementsystem.dto.User;
import com.capgemini.librarymanagementsystem.exception.LibraryManagementSystemException;

public class AdminDAOImplementation implements AdminDAO{

	@Override
	public boolean registerAdmin(Admin admin) {
		for(Admin ad : LibraryDB.ADMIN) {
			if(ad.getEmail().equals(admin.getEmail())) {
				return false;
			}
		}
		LibraryDB.ADMIN.add(admin);
		return true;
	}

	@Override
	public Admin loginAdmin(String email, String password) {
		for(Admin admin : LibraryDB.ADMIN) {
			if(admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
				return admin;
			}
		}
		throw new LibraryManagementSystemException("Invalid credentials");


	}

	@Override
	public boolean addBook(BookDetails book) {
		for(BookDetails b : LibraryDB.BOOK) {
			if(b.getBookId() == book.getBookId()) {
				return false;
			}
		}
		LibraryDB.BOOK.add(book);
		return true;
	}

	@Override
	public boolean removeBook(int id) {
		boolean removeStatus=false;
		for(int i=0;i<=LibraryDB.BOOK.size()-1;i++)
		{
			BookDetails retrievedBook=LibraryDB.BOOK.get(i);
			int retrievedId=retrievedBook.getBookId();
			if(id==retrievedId)
			{
				removeStatus=true;
				LibraryDB.BOOK.remove(i);
				break;
			}
		}
		return removeStatus;
	}//End of removeBook Method

	@Override
	public int updateBook(int AdminId) {
		int updateStatus=0;
		for(int i=0;i<=LibraryDB.BOOK.size()-1;i++)
		{
			BookDetails retrievedBook=LibraryDB.BOOK.get(i);
			int retrievedId=retrievedBook.getBookId();
			if(AdminId==retrievedId)
			{
				updateStatus++;
				break;
			}
		}
		throw new LibraryManagementSystemException("Book not updated");
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
			throw new LibraryManagementSystemException("Book not found");
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
			throw new LibraryManagementSystemException("Book not found");
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

	@Override
	public List<User> showUsers() {
		List<User> usersList = new LinkedList<User>();
		for (User userBean : LibraryDB.USER) {

			userBean.getUserId();
			userBean.getName();
			userBean.getEmail();
			userBean.getBooksBorrowed();
			usersList.add(userBean);

		}
		return usersList;		
	}

	@Override
	public List<Request> showRequests() {
		List<Request> info = new LinkedList<Request>();
		for (Request requestInfo : LibraryDB.REQUEST) {
			requestInfo.getBookInfo();
			requestInfo.getUserInfo();
			requestInfo.isIssued();
			requestInfo.isReturned();
			info.add(requestInfo);
		}
		return info;
	}

	@Override
	public boolean bookIssue(User user, BookDetails bookDetails) {
		boolean isValid = false;

		Request requestInfo = new Request();

		int noOfBooksBorrowed = user.getBooksBorrowed();
		for (Request info : LibraryDB.REQUEST) {
			if (info.getUserInfo().getUserId() == user.getUserId()) {
				if (info.getBookInfo().getBookId() == bookDetails.getBookId()) {
					requestInfo = info;

					isValid = true;

				}
			}
		}

		if (isValid)
		{
			for (BookDetails info2 : LibraryDB.BOOK) {
				if (info2.getBookId() == bookDetails.getBookId()) {
					bookDetails = info2;
				}
			}

			for (User userInfo : LibraryDB.USER) {
				if (userInfo.getUserId() == user.getUserId()) {
					user = userInfo;
					noOfBooksBorrowed = user.getBooksBorrowed();

				}
			}

			if (noOfBooksBorrowed < 3) {

				boolean isRemoved = LibraryDB.BOOK.remove(bookDetails);
				if (isRemoved) {

					noOfBooksBorrowed++;
					System.out.println(noOfBooksBorrowed);
					user.setBooksBorrowed(noOfBooksBorrowed);
					// DataBase.REQUESTDB.remove(requestInfo);
					requestInfo.setIssued(true);
					return true;
				} else {
					throw new LibraryManagementSystemException("Book can't be borrowed");
				}

			} else {
				throw new LibraryManagementSystemException("Student Exceeds maximum limit");
			}

		} else {
			throw new LibraryManagementSystemException("Book data or Student data is incorrect");
		}
	}

	@Override
	public boolean isBookReceived(User user, BookDetails bookDetails) {
		boolean isValid = false;
		Request requestInfo1 = new Request();
		for (Request requestInfo : LibraryDB.REQUEST) {

			if (requestInfo.getBookInfo().getBookId() == bookDetails.getBookId()
					&& requestInfo.getUserInfo().getUserId() == user.getUserId() 
					&& requestInfo.isReturned() == true) {
				isValid = true;
				requestInfo1 = requestInfo;
			}
		}
		if (isValid) {

			bookDetails.setAuthorName(requestInfo1.getBookInfo().getAuthorName());
			bookDetails.setBookName(requestInfo1.getBookInfo().getBookName());
			LibraryDB.BOOK.add(bookDetails);
			LibraryDB.REQUEST.remove(requestInfo1);


			for (User userInfo2 : LibraryDB.USER) {
				if (userInfo2.getUserId() == user.getUserId()) {
					user = userInfo2;
				}
			}
			int noOfBooksBorrowed = user.getBooksBorrowed();
			noOfBooksBorrowed--;
			user.setBooksBorrowed(noOfBooksBorrowed);
			return true;
		}
		return false;
	}


}


