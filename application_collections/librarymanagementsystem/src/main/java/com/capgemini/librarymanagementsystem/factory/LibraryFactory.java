package com.capgemini.librarymanagementsystem.factory;

import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.dao.AdminDAOImplementation;
import com.capgemini.librarymanagementsystem.dao.UserDAO;
import com.capgemini.librarymanagementsystem.dao.UserDAOImplementation;
import com.capgemini.librarymanagementsystem.service.AdminService;
import com.capgemini.librarymanagementsystem.service.AdminServiceImplementation;
import com.capgemini.librarymanagementsystem.service.UserService;
import com.capgemini.librarymanagementsystem.service.UserServiceImplementation;

public class LibraryFactory {
	public static AdminDAO getAdminDao() {
		return new AdminDAOImplementation();
	}
	public static AdminService getAdminService() {
		return new AdminServiceImplementation();
	}
	public static UserDAO getUserDao() {
		return new UserDAOImplementation();
	}
	public static UserService getUserService() {
		return new UserServiceImplementation();
	}

}
