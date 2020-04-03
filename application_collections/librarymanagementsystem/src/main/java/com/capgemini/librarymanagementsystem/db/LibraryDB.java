package com.capgemini.librarymanagementsystem.db;

import java.util.LinkedList;

import com.capgemini.librarymanagementsystem.dao.UserDAO;
import com.capgemini.librarymanagementsystem.dto.Admin;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.Request;
import com.capgemini.librarymanagementsystem.dto.User;

public class LibraryDB {
	public static final LinkedList<BookDetails> BOOK = new LinkedList<BookDetails>(); 
	public static final LinkedList<Admin> ADMIN = new LinkedList<Admin>();
	public static final LinkedList<User> USER = new LinkedList<User>();
	public static final LinkedList<Request> REQUEST = new LinkedList<Request>();	
}