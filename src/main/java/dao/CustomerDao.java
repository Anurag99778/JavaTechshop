package dao;

import java.sql.*;

import controller.CustomerController;
import entities.Customers;
import util.DBConnection;

public class CustomerDao {
	Connection connection;
	Statement statement;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	Customers customer;
	
	public void putCustomersToArray() {
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select * from Customers");
			while(resultSet.next()) {
				customer=new Customers();
				customer.setCustomerId(resultSet.getInt(1));
				customer.setFirstName(resultSet.getString(2));
				customer.setLastName(resultSet.getString(3));
				customer.setEmail(resultSet.getString(4));
				customer.setPhone(resultSet.getString(5));
				customer.setAddress(resultSet.getString(6));
				CustomerController.customerList.add(customer);
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	public void insertCustomer(Customers c) {
		try {
			connection= DBConnection.getMyDBConnection();
			preparedStatement=connection.prepareStatement("insert into Customers values(?,?,?,?,?,?)");
			preparedStatement.setInt(1, c.getCustomerId());
			preparedStatement.setString(2, c.getFirstName());
			preparedStatement.setString(3, c.getLastName());
			preparedStatement.setString(4, c.getEmail());
			preparedStatement.setString(5, c.getPhone());
			preparedStatement.setString(6,c.getAddress());
			int noofrows = preparedStatement.executeUpdate();
			System.out.println(noofrows + " inserted successfully !!!");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void displayCustomer(int id) {
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			String query="select * from Customers where customerid="+id;
			resultSet=statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Customer Id  : " + resultSet.getInt(1));
				System.out.println("Customer Name  : " + resultSet.getString(2)+" "+resultSet.getString(3));
				System.out.println("Customer Email  : " + resultSet.getString(4));
				System.out.println("Customer phone  : " + resultSet.getString(5));
				System.out.println("Customer address  : " + resultSet.getString(6));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	public void displayAllCustomers() {
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select * from Customers");
			while(resultSet.next()) {
				System.out.println("Customer Id  : " + resultSet.getInt(1));
				System.out.println("Customer Name  : " + resultSet.getString(2)+" "+resultSet.getString(3));
				System.out.println("Customer Email  : " + resultSet.getString(4));
				System.out.println("Customer phone  : " + resultSet.getString(5));
				System.out.println("Customer address  : " + resultSet.getString(6));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	public void updateCustomer(int id, String firstname, String lastname, String email, String phone, String address) {
		try {
			connection= DBConnection.getMyDBConnection();
			preparedStatement=connection.prepareStatement("update Customers set firstname='"+firstname+"',lastname='"+lastname+
					"',email='"+email+"',phno='"+phone+"',address='"+address+"' where customerid="+id);
			
			int noofrows = preparedStatement.executeUpdate();
			System.out.println(noofrows + " Updated successfully !!!");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void calculateTotalOrders(int id) {
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select count(*),c.firstname,c.lastname from Customers c join Orders o on c.customerid=o.customerid where c.customerid="+id+" group by c.firstname");
			while(resultSet.next()) {
				System.out.println("Order Count  : " + resultSet.getInt(1));
				System.out.println("Customer Name  : " + resultSet.getString(2)+" "+resultSet.getString(3));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
}
