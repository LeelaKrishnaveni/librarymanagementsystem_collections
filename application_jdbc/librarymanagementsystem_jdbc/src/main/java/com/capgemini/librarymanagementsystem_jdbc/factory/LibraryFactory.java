package com.capgemini.librarymanagementsystem_jdbc.factory;

import com.capgemini.librarymanagementsystem_jdbc.dao.UserDetailsDAO;
import com.capgemini.librarymanagementsystem_jdbc.dao.UsersDAOImplementation;
import com.capgemini.librarymanagementsystem_jdbc.service.UserService;
import com.capgemini.librarymanagementsystem_jdbc.service.UserServiceImplementation;

public class LibraryFactory {
	public static UserDetailsDAO getUserDAO() {
		return new UsersDAOImplementation();
	}
	public static UserService getUserService() {
		return  new UserServiceImplementation();
	}
}
