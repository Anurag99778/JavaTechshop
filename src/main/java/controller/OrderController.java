package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.OrderDao;
import entities.Customers;
import entities.OrderDetails;
import entities.Orders;
import exceptions.DuplicateDataException;
import exceptions.IncompleteOrderException;

public class OrderController {

	private Orders order;
	OrderDetailsController orderDetailsControllerObject;
	OrderDao orderDataAccessObject=new OrderDao();
	InputStreamReader inputStreamReader=new InputStreamReader(System.in);
	BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
	public static List<Orders> orderList=new ArrayList<>(); 
	
	public void putOrdersToArray() {
		orderDataAccessObject.putOrdersToArray();
	}
	
	//add order 
	public void addOrder() {
		order=new Orders();
		try {
				StringBuffer temp=new StringBuffer(""); 
				System.out.print("Enter Order id: ");
				temp=temp.append(bufferedReader.readLine());
				if(temp.length()==0) {
					throw new IncompleteOrderException();
				}
				int id=Integer.parseInt(temp.toString());
				order.setOrderId(id);
				System.out.println();
				System.out.print("Enter Customer id: ");
				temp.setLength(0);
				temp=temp.append(bufferedReader.readLine());
				if(temp.length()==0) {
					throw new IncompleteOrderException();
				}
				int sendCustomerId=Integer.parseInt(temp.toString());
				
				for(Customers a:CustomerController.customerList) {
					if(a.getCustomerId()==sendCustomerId) {
						//o=new Orders(a,id,dt,tm,st);
						order.setCustomer(a);
						break;
					}
				}
				System.out.println();
				System.out.print("Enter Order Date(yyyy-mm-dd): ");
				temp.setLength(0);
				temp=temp.append(bufferedReader.readLine());
				if(temp.length()==0) {
					throw new IncompleteOrderException();
				}
				LocalDate date=LocalDate.parse(temp.toString());
				order.setOrderDate(date);
				System.out.println();
				System.out.print("Enter Order Status: ");
				temp.setLength(0);
				temp=temp.append(bufferedReader.readLine());
				if(temp.length()==0) {
					throw new IncompleteOrderException();
				}
				order.setStatus(temp.toString());
				System.out.println();
				System.out.print("Enter Total Amount:");
				temp.setLength(0);
				temp=temp.append(bufferedReader.readLine());
				if(temp.length()==0) {
					throw new IncompleteOrderException();
				}
				double total_amount=Double.parseDouble(temp.toString());

				order.setTotalAmount(total_amount);
				System.out.println();
				
				boolean containsObject = orderList.stream()
		                .anyMatch(obj -> obj.getOrderId()==order.getOrderId());
				
				
				if(containsObject) {
					throw new DuplicateDataException();
				}
				orderList.add(order);
				orderDataAccessObject.insertOrder(order);
				Collections.sort(orderList);
				
				}catch(IOException e) {
					e.printStackTrace();
				}
				catch(IncompleteOrderException e) {
					System.err.println(e);
				} catch (DuplicateDataException e) {
					// TODO Auto-generated catch block
					System.err.println(e);
				}catch(Exception e) {
					e.printStackTrace();
				}
	}
	
		public void calculateTotalAmount(int orderId) {
	        // Implementation to calculate total amount of the order
	        //quantity and price from orderdetailsController
			double tam=0.0;
			orderDetailsControllerObject=new OrderDetailsController();
			for(OrderDetails i:orderDetailsControllerObject.getOrderDetailsList()) {
				if(i.getOrder().getOrderId()==orderId) {
					tam=i.getQuantity()*i.getProduct().getPrice();
					System.out.println("Total Amount is: "+tam);
					i.getOrder().setTotalAmount(tam);
					break;
				}
			}
			orderDataAccessObject.calculateTotalAmount(orderId);
		}
		
		public void getOrderDetails(int orderId) {
	        // Implementation to retrieve and display order details
//			odc=new OrderDetailsController();
//			for(OrderDetails o:odc.getOrderDetailsList()) {
//	        	if(o.getOrder().getOrderId()==oid) {
//	        		System.out.println(o);
//	        	}
//	        }
			orderDataAccessObject.getOrderDetails(orderId);
	    }
	
		public void showAllOrders() {
			Collections.sort(orderList);
			System.out.println(orderList);
			//odao.showAllOrders();
		}
		
		public void getOrderStatus(int orderId) {
			for(Orders o:orderList) {
				if(o.getOrderId()==orderId) {
					System.out.println("Order is "+o.getStatus());
					return;
				}
			}
		}
		
	 	public void updateOrderStatus(int orderId,String status) {
	        // Implementation to update order status
	        // Logic to update order status in the database
		 	for(Orders o:orderList) {
		 		if(o.getOrderId()==orderId) {
		 			o.setStatus(status);
		 			break;
		 		}
		 	}
		 	orderDataAccessObject.updateOrderStatus(orderId, status);
	 	}

	 	//
	    public void cancelOrder(int orderId) {
	    	for(Orders o:orderList) {
		 		if(o.getOrderId()==orderId) {
		 			orderList.remove(o);
		 			break;
		 		}
		 	}
	    	orderDataAccessObject.cancelOrder(orderId);
	    }
	    
	    
	    //return all orders
		public List<Orders> getOrderList(){
			return orderList;
		}
}
