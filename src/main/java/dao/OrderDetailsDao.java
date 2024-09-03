package dao;

import java.sql.*;

import controller.OrderController;
import controller.OrderDetailsController;
import controller.ProductController;
import entities.OrderDetails;
import entities.Orders;
import entities.Customers;
import controller.CustomerController;

import entities.Products;
import util.DBConnection;

public class OrderDetailsDao {
	Connection connection;
	Statement statement;
	PreparedStatement ps;
	PreparedStatement preparedStatement;
	Orders order;
	ResultSet resultSet;

	public void putOrdersToArray() {
		try {
			connection = DBConnection.getMyDBConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from Orders");
			while (resultSet.next()) {
				order = new Orders();
				order.setOrderId(resultSet.getInt(1));
				for (Customers c : CustomerController.customerList) {
					if (c.getCustomerId() == resultSet.getInt(2)) {
						order.setCustomer(c);
						break;
					}
				}
				order.setOrderDate(resultSet.getDate(3).toLocalDate());
				order.setStatus(resultSet.getString(4));
				order.setTotalAmount(resultSet.getDouble(5));
				order.setPaymentMethod(resultSet.getString(6));
				order.setPaymentStatus(resultSet.getString(7));
				order.setPaymentDate(resultSet.getDate(8).toLocalDate());
				order.setPaymentAmount(resultSet.getDouble(9));

				OrderController.orderList.add(order);
			}
		} catch (SQLException e) {
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
	
	public void calculateSubTotal(int odid) {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			String query="select (od.quantity * p.price) AS subtotal FROM OrderDetails od JOIN Products p ON od.productid = p.productid WHERE od.orderdetailid ="+odid;
			resultSet= preparedStatement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Order Detail Subtotal  : " + resultSet.getDouble(1));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void getOrderDetailInfo(int odid) {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			String query="select * from OrderDetails where orderdetailid="+odid;
			resultSet=statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Order Detail Id  : " + resultSet.getInt(1));
				System.out.println("Order Id  : " + resultSet.getInt(2));
				System.out.println("Product Id  : " + resultSet.getInt(3));
				System.out.println("Order Quantity  : " + resultSet.getInt(4));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}

	public void getAllOrderDetailInfo() {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			String query="select * from OrderDetails";
			resultSet=statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Order Detail Id  : " + resultSet.getInt(1));
				System.out.println("Order Id  : " + resultSet.getInt(2));
				System.out.println("Product Id  : " + resultSet.getInt(3));
				System.out.println("Order Quantity  : " + resultSet.getInt(4));
				System.out.println();
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	public void updateQuantity(int odid,int quant) {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			preparedStatement=connection.prepareStatement("update OrderDetails set quantity="+quant+" where orderdetailid="+odid);
			
			int noofrows = preparedStatement.executeUpdate();
			System.out.println(noofrows + "Order Detail Quantity Updated successfully !!!");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addDiscount(int odid,double discount) {
		try {
			connection= DBConnection.getMyDBConnection();
			String query="select (o.totalamount-(o.totalamount*"+discount+")) discounted_price from OrderDetails od join Orders o on od.orderid=o.orderid where od.orderdetailid="+odid;
			preparedStatement=connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				System.out.println("Discount applied!!!\n"
						+ "New Discounted price: "+resultSet.getDouble(1));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
