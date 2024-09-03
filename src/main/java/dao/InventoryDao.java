package dao;

import java.sql.*;

import controller.InventoryController;
import controller.ProductController;
import entities.Inventory;
import entities.Products;
import util.DBConnection;

public class InventoryDao {
	Connection connection;
	Statement statement;
	PreparedStatement preparedStatement;
	Inventory inventory;
	ResultSet resultSet;
	
	public void putInventoryToArray() {
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select * from Inventory");
			while(resultSet.next()) {
				inventory=new Inventory();
				inventory.setInventoryId(resultSet.getInt(1));
				for(Products p:ProductController.productsList) {
					if(p.getProductId()==resultSet.getInt(2)) {
						inventory.setProduct(p);
						break;
					}
				inventory.setQuantityInStock(resultSet.getInt(3));
				inventory.setLastStockUpdate(resultSet.getDate(4).toLocalDate());
				}
				InventoryController.inventoryList.add(inventory);
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	public void addToInventory(Inventory i) {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			preparedStatement=connection.prepareStatement("insert into Inventory values(?,?,?,?)");
			preparedStatement.setInt(1, i.getInventoryId());
			preparedStatement.setInt(2, i.getProduct().getProductId());
			preparedStatement.setInt(3, i.getQuantityInStock());
			Date date=Date.valueOf(i.getLastStockUpdate());
			preparedStatement.setDate(4, date);
			int noofrows = preparedStatement.executeUpdate();
			System.out.println(noofrows + "Product added to inventory !!!");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


	public void getProduct(int pid) {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			String query="select * from Inventory where productid="+pid;
			resultSet=statement.executeQuery(query);
			while(resultSet.next()) {
				System.out.println("Inventory Id  : " + resultSet.getInt(1));
				System.out.println("Product Id  : " + resultSet.getInt(2));
				System.out.println("Quantity In Sotck  : " + resultSet.getInt(3));
				System.out.println("Last Stock Updated on  : " + resultSet.getDate(4));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}

	public void getQuantityInStock(int pid) {
		try {
			connection=DBConnection.getMyDBConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select quantityinstock from Inventory where productid="+pid);
			while(resultSet.next()) {
				System.out.println("Products quantity in stock is: "+resultSet.getInt(1));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeFromInventory(int pid,int quant) {
		try {
			connection= DBConnection.getMyDBConnection();
			preparedStatement=connection.prepareStatement("update Inventory set quantityinstock = quantityinstock-"+quant+" where productid="+pid);
			int noofrows = preparedStatement.executeUpdate();
			System.out.println(noofrows + "Quantity Removed from Inventory !!!");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateStockQuantity(int pid,int quant) {
		try {
			connection= DBConnection.getMyDBConnection();
			preparedStatement=connection.prepareStatement("update Inventory set quantityinstock = quantityinstock+"+quant+" where productid="+pid);
			int noofrows = preparedStatement.executeUpdate();
			System.out.println(noofrows + "Quantity Updated in Inventory !!!");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isProductAvailable(int pid,int quant) {
		try {
			connection=DBConnection.getMyDBConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select 1 from inventory where quantityinstock>="+quant+" and productid="+pid);
			while(resultSet.next()) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void getInventoryValue() {
		try {
			connection=DBConnection.getMyDBConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select SUM(i.quantityinstock*p.price) from Inventory i join Products p on i.productid=p.productid");
			while(resultSet.next()) {
				System.out.println("Total inventory value is: "+resultSet.getDouble(1));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void listLowStockProducts(int quant) {
		try {
			connection=DBConnection.getMyDBConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select p.pname,i.quantityinstock from inventory i join products p on p.productid=i.productid where i.quantityinstock<"+quant);
			while(resultSet.next()) {
				System.out.println("Product Name: "+resultSet.getString(1));
				System.out.println("Quantity in stock: "+resultSet.getInt(2));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void listOutOfStockProducts() {
		try {
			connection=DBConnection.getMyDBConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select p.pname,i.quantityinstock from inventory i join products p on p.productid=i.productid where i.quantityinstock=0");
			
				while(resultSet.next()) {
					System.out.println("Product Name: "+resultSet.getString(1));
					System.out.println("Quantity in stock: "+resultSet.getInt(2));
				}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void listAllInventoryProducts() {
		// TODO Auto-generated method stub
		try {
			connection= DBConnection.getMyDBConnection();
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select p.pname from Inventory i join Products p on i.productid=p.productid");
			while(resultSet.next()) {
				System.out.println("Product Name  : " + resultSet.getString(1));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
}
