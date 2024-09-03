package dao;

import java.sql.*;

import controller.CustomerController;
import controller.OrderController;
import entities.Customers;
import entities.Orders;
import util.DBConnection;

public class OrderDao {
	Connection connection;
	Statement statement;
	PreparedStatement ps;
	Orders order;
	ResultSet resultSet;
	
	public void putOrdersToArray() {
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select * from Orders");
			while(resultSet.next()) {
				order=new Orders();
				order.setOrderId(resultSet.getInt(1));
				for(Customers c:CustomerController.customerList) {
					if(c.getCustomerId()==resultSet.getInt(2)) {
						order.setCustomer(c);
						break;
					}
				}
				order.setOrderDate(resultSet.getDate(3).toLocalDate());
				order.setStatus(resultSet.getString(4));
				order.setTotalAmount(resultSet.getDouble(5));
				
				OrderController.orderList.add(order);
			}
		}catch (SQLException e) {
		
			e.printStackTrace();
		}
	}

	public void insertOrder(Orders o) {
		if (o != null) {
			try {
				connection = DBConnection.getMyDBConnection();

				// Updated SQL statement to include payment information
				ps = connection.prepareStatement("INSERT INTO Orders (OrderID, CustomerID, OrderDate, Status, TotalAmount, PaymentMethod, PaymentStatus, PaymentDate, PaymentAmount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

				ps.setInt(1, o.getOrderId());
				ps.setInt(2, o.getCustomer().getCustomerId());
				Date orderDate = Date.valueOf(o.getOrderDate());
				ps.setDate(3, orderDate);
				ps.setString(4, o.getStatus());
				ps.setDouble(5, o.getTotalAmount());

				// Set payment information
				ps.setString(6, o.getPaymentMethod());
				ps.setString(7, o.getPaymentStatus());
				Date paymentDate = Date.valueOf(o.getPaymentDate());
				ps.setDate(8, paymentDate);
				ps.setDouble(9, o.getPaymentAmount());

				int noofrows = ps.executeUpdate();
				System.out.println(noofrows + " Order inserted successfully !!!");

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public void showAllOrders() {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select * from Orders");
			while(resultSet.next()) {
				System.out.println("Order Id  : " + resultSet.getInt(1));
				System.out.println("Customer ID  : " + resultSet.getString(2));
				System.out.println("Order date  : " + resultSet.getDate(3));
				System.out.println("Order Status  : " + resultSet.getString(4));
				System.out.println("Order Total Amount  : " + resultSet.getDouble(5));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}

	public void getOrderDetails(int oid) {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			String query="select * from Orders where orderid="+oid;
			resultSet=statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Order Id  : " + resultSet.getInt(1));
				System.out.println("Customer Id  : " + resultSet.getInt(2));
				System.out.println("Order Date  : " + resultSet.getDate(3));
				System.out.println("Order Status  : " + resultSet.getString(4));
				System.out.println("Total Amount  : " + resultSet.getDouble(5));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}

	public void updateOrderStatus(int oid, String status) {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			ps=connection.prepareStatement("update Orders set ostatus='"+status+"' where orderid="+oid);
			
			int noofrows = ps.executeUpdate();
			System.out.println(noofrows + "Order Status Updated successfully !!!");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cancelOrder(int oid) {
		try {
			connection= DBConnection.getMyDBConnection();
			ps=connection.prepareStatement("Delete from Orders"+" where orderid="+oid);
			
			int noofrows = ps.executeUpdate();
			System.out.println(noofrows + "Order Cancelled !!!");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void calculateTotalAmount(int oid) {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			String query="update Orders set totalamount = (SELECT SUM(od.quantity * p.price) AS new_total FROM OrderDetails od JOIN Products p ON od.productid = p.productid WHERE od.orderid ="+oid+" GROUP BY od.orderid) WHERE orderid ="+oid;
			ps=connection.prepareStatement(query);			
			int noofrows = ps.executeUpdate();
			System.out.println(noofrows + "Total Amount Updated!!!");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
