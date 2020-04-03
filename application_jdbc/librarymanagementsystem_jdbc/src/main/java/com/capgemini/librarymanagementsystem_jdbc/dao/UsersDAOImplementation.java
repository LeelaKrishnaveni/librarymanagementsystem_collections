package com.capgemini.librarymanagementsystem_jdbc.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.capgemini.librarymanagementsystem_jdbc.dto.BookDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.BookIssueDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.BooksBorrowed;
import com.capgemini.librarymanagementsystem_jdbc.dto.RequestDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.UserDetails;
import com.capgemini.librarymanagementsystem_jdbc.exception.LibraryManagementSystemException;

public class UsersDAOImplementation implements UserDetailsDAO {
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet resultSet = null;
	Statement statement= null;

	@Override
	public boolean register(UserDetails user) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("insert into userdetails values(?,?,?,?,?,?,?)")){
				pstmt.setInt(1,user.getuId());
				pstmt.setString(2, user.getFirstName());
				pstmt.setString(3, user.getLastName());
				pstmt.setString(4, user.getEmail());
				pstmt.setString(5, user.getPassword());
				pstmt.setLong(6, user.getMobileNumber());
				pstmt.setString(7, user.getRole());
				int count = pstmt.executeUpdate();
				if(user.getEmail().isEmpty() && count==0) {
					return false;
				} else {
					return true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public UserDetails login(String email, String password) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from userdetails where email=? and password=?");) {
				pstmt.setString(1,email);
				pstmt.setString(2,password);
				resultSet=pstmt.executeQuery();
				if(resultSet.next()) {
					UserDetails bean = new UserDetails();
					bean.setuId(resultSet.getInt("uId"));
					bean.setFirstName(resultSet.getString("firstName"));
					bean.setLastName(resultSet.getString("lastName"));
					bean.setEmail(resultSet.getString("email"));
					bean.setPassword(resultSet.getString("password"));
					bean.setMobileNumber(resultSet.getLong("mobileNumber"));
					bean.setRole(resultSet.getString("role"));
					return bean;
				} else {
					return null;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addBook(BookDetails book) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("insert into bookdetails values(?,?,?,?,?)");) {
				pstmt.setInt(1, book.getBId());
				pstmt.setString(2, book.getBookName());
				pstmt.setString(3, book.getAuthorName());
				pstmt.setString(4, book.getCategory());
				pstmt.setString(5, book.getPublisher());
				//pstmt.setInt(6, book.getCopies());
				int count = pstmt.executeUpdate();
				if(count!=0) {
					return true;
				} else {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean removeBook(int bId) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("delete from bookdetails where bid=?");) {
				pstmt.setInt(1,bId);
				int count=pstmt.executeUpdate();
				if(count!=0) {
					return true;
				} else {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean updateBook(BookDetails book) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("update bookdetails set bookname=? where bid=?");) {
				pstmt.setString(1,book.getBookName());
				pstmt.setInt(2,book.getBId());
				int count=pstmt.executeUpdate();
				if(count!=0) {
					return true;
				} else {
					return false;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean issueBook(int bId, int uId) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from request_details where uid=? and bid=? and email=(select email from userdetails where uid=?)")) {
				pstmt.setInt(1, uId);
				pstmt.setInt(2, bId);
				pstmt.setInt(3, uId);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					try(PreparedStatement pstmt1 = conn.prepareStatement("insert into books_issue_details values(?,?,?,?)");){
						pstmt1.setInt(1, bId);
						pstmt1.setInt(2, uId);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
						Calendar cal = Calendar.getInstance();
						String issueDate = sdf.format(cal.getTime());
						pstmt1.setDate(3, java.sql.Date.valueOf(issueDate));
						cal.add(Calendar.DAY_OF_MONTH, 7);
						String returnDate = sdf.format(cal.getTime());
						pstmt1.setDate(4, java.sql.Date.valueOf(returnDate));
						int count=pstmt1.executeUpdate();
						if(count != 0) {	
							try(PreparedStatement pstmt2 = conn.prepareStatement("Insert into books_borrowed values(?,?,(select email from userdetails where uid=?))")){
								pstmt2.setInt(1, uId);
								pstmt2.setInt(2, bId);
								pstmt2.setInt(3, uId);
								int isBorrowed = pstmt2.executeUpdate();
								if(isBorrowed != 0) {
									return true;
								}else {
									return false;
								}
							}
						} else {
							throw new LibraryManagementSystemException("Book Not issued");
						}					
					}
				} else {
					throw new LibraryManagementSystemException("The respective user have not placed any request");
				}			
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean request(int uId, int bId) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select count(*) as uid from books_borrowed where uid=? and bid=? and email=(select email from userdetails where uid=?)");) {
				pstmt.setInt(1, uId);
				pstmt.setInt(2, bId);
				pstmt.setInt(3, uId);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					int isBookExists = rs.getInt("uId");
					if(isBookExists==0) {
						try(PreparedStatement pstmt1 = conn.prepareStatement("select count(*) as uid from books_issue_details where uid=?");) {
							pstmt1.setInt(1, uId);
							rs=pstmt1.executeQuery();
							if(rs.next()) {
								int noOfBooksBorrowed = rs.getInt("uId");
								if(noOfBooksBorrowed<3) {
									try(PreparedStatement pstmt2 = conn.prepareStatement("insert into request_details values(?,(select concat(firstname,'_',lastname) from userdetails where uid=?)"
											+ ",?,(select bookname from bookdetails where bid=?),(select email from userdetails where uid=?))");){
										pstmt2.setInt(1,uId);
										pstmt2.setInt(2, uId);
										pstmt2.setInt(3, bId);
										pstmt2.setInt(4, bId);
										pstmt2.setInt(5, uId);
										int count = pstmt2.executeUpdate();
										if(count != 0) {
											return true;
										}else {
											return false;
										}
									}				 
								}else {
									throw new LibraryManagementSystemException("no Of books limit has crossed");
								}
							}else {
								throw new LibraryManagementSystemException("no of books limit has crossed");
							}		
						}				
					}else{
						throw new LibraryManagementSystemException("You have already borrowed the requested book");
					}		
				}else {
					throw new LibraryManagementSystemException("You have already borrowed the requested book");
				}			
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<BooksBorrowed> bookBorrowed(int uId) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from books_borrowed where uid=?");) {
				pstmt.setInt(1, uId);
				resultSet=pstmt.executeQuery();
				LinkedList<BooksBorrowed> beans = new LinkedList<BooksBorrowed>();
				while(resultSet.next()) {
					BooksBorrowed listOfbooksBorrowed = new BooksBorrowed();
					listOfbooksBorrowed.setuId(resultSet.getInt("uId"));
					listOfbooksBorrowed.setbId(resultSet.getInt("bId"));
					listOfbooksBorrowed.setEmail(resultSet.getString("email"));
					beans.add(listOfbooksBorrowed);
				} 
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}


	}

	@Override
	public LinkedList<BookDetails> searchBookById(int bId) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from bookdetails where bid=?");) {
				pstmt.setInt(1,bId);
				resultSet=pstmt.executeQuery();
				LinkedList<BookDetails> beans = new LinkedList<BookDetails>();
				while(resultSet.next()) {
					BookDetails bean = new BookDetails();
					bean.setBId(resultSet.getInt("bId"));
					bean.setBookName(resultSet.getString("bookName"));
					bean.setAuthorName(resultSet.getString("authorName"));
					bean.setCategory(resultSet.getString("category"));
					bean.setPublisher(resultSet.getString("publisher"));
					//bean.setCopies(rs.getInt("copies"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedList<BookDetails> searchBookByTitle(String bookName) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from bookdetails where bookname=?");) {
				pstmt.setString(1,bookName);
				resultSet=pstmt.executeQuery();
				LinkedList<BookDetails> beans = new LinkedList<BookDetails>();
				while(resultSet.next()) {
					BookDetails bean = new BookDetails();
					bean.setBId(resultSet.getInt("bId"));
					bean.setBookName(resultSet.getString("bookName"));
					bean.setAuthorName(resultSet.getString("authorName"));
					bean.setCategory(resultSet.getString("category"));
					bean.setPublisher(resultSet.getString("publisher"));
					//bean.setCopies(rs.getInt("copies"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}


	}
	@Override
	public LinkedList<BookDetails> searchBookByAuthor(String authorName) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from bookdetails where authorName=?");) {
				pstmt.setString(1,authorName);
				resultSet=pstmt.executeQuery();
				LinkedList<BookDetails> beans = new LinkedList<BookDetails>();
				while(resultSet.next()) {
					BookDetails bean = new BookDetails();
					bean.setBId(resultSet.getInt("bId"));
					bean.setBookName(resultSet.getString("bookName"));
					bean.setAuthorName(resultSet.getString("authorName"));
					bean.setCategory(resultSet.getString("category"));
					bean.setPublisher(resultSet.getString("publisher"));
					//bean.setCopies(rs.getInt("copies"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}


	}

	@Override
	public LinkedList<BookDetails> getBooksInfo() {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					Statement stmt = (Statement)conn.createStatement();) {
				resultSet = stmt.executeQuery("select * from bookdetails");
				LinkedList<BookDetails> beans = new LinkedList<BookDetails>();
				while(resultSet.next()) {
					BookDetails bean = new BookDetails();
					bean.setBId(resultSet.getInt("bId"));
					bean.setBookName(resultSet.getString("bookName"));
					bean.setAuthorName(resultSet.getString("authorName"));
					bean.setCategory(resultSet.getString("category"));
					bean.setPublisher(resultSet.getString("publisher"));
					//bean.setCopies(rs.getInt("copies"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	@Override
	public boolean returnBook(int bId, int uId,String status) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select * from books_issue_details where bid=? and uid=?");) {
				pstmt.setInt(1, bId);
				pstmt.setInt(2, uId);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					Date issueDate = rs.getDate("issueDate");
					Date returnDate = rs.getDate("returnDate");
					long difference = issueDate.getTime() - returnDate.getTime();
					float daysBetween = (difference / (1000*60*60*24));
					if(daysBetween>7) {
						float fine = daysBetween*5;
						System.out.println("The user has to pay the fine of the respective book of Rs:"+fine);
						if(status=="yes") {
							try(PreparedStatement pstmt1 = conn.prepareStatement("delete from books_issue_details where bid=? and uid=?");) {
								pstmt1.setInt(1,bId);
								pstmt1.setInt(2,uId);
								int count =  pstmt1.executeUpdate();
								if(count != 0) {
									try(PreparedStatement pstmt2 = conn.prepareStatement("delete from books_borrowed where bid=? and uid=?");) {
										pstmt2.setInt(1, bId);
										pstmt2.setInt(2, uId);
										int isReturned = pstmt2.executeUpdate();
										if(isReturned != 0 ) {
											try(PreparedStatement pstmt3 = conn.prepareStatement("delete from request_details where bid=? and uid=?");){
												pstmt3.setInt(1, bId);
												pstmt3.setInt(2, uId);
												int isRequestDeleted = pstmt3.executeUpdate();
												if(isRequestDeleted != 0) {
													return true;
												}else {
													return false;
												}
											}
										}else {
											return false;
										}
									}
								} else {
									return false;
								}
							}
						} else {
							throw new LibraryManagementSystemException("The User has to pay fine for delaying book return");
						}
					}else {
						try(PreparedStatement pstmt1 = conn.prepareStatement("delete from book_issue_details where bid=? and uid=?");) {
							pstmt1.setInt(1,bId);
							pstmt1.setInt(2,uId);
							int count =  pstmt1.executeUpdate();
							if(count != 0) {
								try(PreparedStatement pstmt2 = conn.prepareStatement("delete from borrowed_books where bid=? and uid=?");) {
									pstmt2.setInt(1, bId);
									pstmt2.setInt(2, uId);
									int isReturned = pstmt2.executeUpdate();
									if(isReturned != 0 ) {
										try(PreparedStatement pstmt3 = conn.prepareStatement("delete from request_details where bid=? and uid=?");){
											pstmt3.setInt(1, bId);
											pstmt3.setInt(2, uId);
											int isRequestDeleted = pstmt3.executeUpdate();
											if(isRequestDeleted != 0) {
												return true;
											}else {
												return false;
											}
										}
									}else {
										return false;
									}
								}
							} else {
								return false;
							}
						}
					}
				}else {
					throw new LibraryManagementSystemException("This respective user hasn't borrowed any book");
				}
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	@Override
	public LinkedList<BookIssueDetails> bookHistoryDetails(int uId) {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pstmt = conn.prepareStatement("select count(*) as uid from books_issue_details where uid=?");) {
				pstmt.setInt(1, uId);
				resultSet=pstmt.executeQuery();
				LinkedList<BookIssueDetails> beans = new LinkedList<BookIssueDetails>();
				while(resultSet.next()) {
					BookIssueDetails issueDetails = new BookIssueDetails();
					issueDetails.setUserId(resultSet.getInt("uId"));
					beans.add(issueDetails);
				} 
				return beans;
			}
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}


	}

	@Override
	public LinkedList<RequestDetails> showRequests() {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					Statement stmt = (Statement)conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from request_details");) {
				LinkedList<RequestDetails> beans = new LinkedList<RequestDetails>();
				while(resultSet.next()) {
					RequestDetails bean = new RequestDetails();
					bean.setuId(resultSet.getInt("uId"));
					bean.setFullName(resultSet.getString("fullName"));
					bean.setbId(resultSet.getInt("bId"));
					bean.setBookName(resultSet.getString("bookName"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	@Override
	public LinkedList<UserDetails> showUsers() {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					Statement stmt = (Statement)conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from userdetails");) {
				LinkedList<UserDetails> beans = new LinkedList<UserDetails>();
				while(rs.next()) {
					UserDetails bean = new UserDetails();
					bean.setuId(rs.getInt("uId"));
					bean.setFirstName(rs.getString("firstName"));
					bean.setLastName(rs.getString("lastName"));
					bean.setEmail(rs.getString("email"));
					bean.setPassword(rs.getString("password"));
					bean.setMobileNumber(rs.getLong("mobile"));
					bean.setRole(rs.getString("role"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public LinkedList<BookIssueDetails> showIssuedBooks() {

		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					Statement stmt = (Statement)conn.createStatement();
					ResultSet resultSet = stmt.executeQuery("select * from book_issue_details");) {
				LinkedList<BookIssueDetails> beans = new LinkedList<BookIssueDetails>();
				while(resultSet.next()) {
					BookIssueDetails bean = new BookIssueDetails();
					bean.setBookId(resultSet.getInt("bookId"));
					bean.setUserId(resultSet.getInt("userId"));
					bean.setIssueDate(resultSet.getDate("issueDate"));
					bean.setReturnDate(resultSet.getDate("returnDate"));
					beans.add(bean);
				}
				return beans;
			}
		} catch(Exception e) {
			//e.printStackTrace();
			System.err.println(e.getMessage());
			return null;
		}

	}

	@Override
	public boolean updatePassword(String email, String password, String newPassword, String role) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pst = conn.prepareStatement("select * from users where email=? and role=?")){
				pst.setString(1, email);
				pst.setString(2, role);
				resultSet=pst.executeQuery();
				if(resultSet.next()) {
					try(PreparedStatement pstmt = conn.prepareStatement("update userdetails set password=? where email=? and password=?");) {
						pstmt.setString(1, newPassword);
						pstmt.setString(2, email);
						pstmt.setString(3,password);
						int count=pstmt.executeUpdate();
						if(count!=0) {
							return true;
						} else {
							return false;
						}
					}
				}else {
					throw new LibraryManagementSystemException("User doesnt exist");
				}
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}


}







