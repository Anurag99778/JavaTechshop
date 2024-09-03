package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dao.OrderDetailsDao;
import entities.OrderDetails;
import entities.Orders;
import entities.Products;
import exceptions.DuplicateDataException;
import exceptions.IncompleteOrderException;
import exceptions.InsufficientStockException;

public class OrderDetailsController {
	OrderDetails orderDetail;
	OrderController orderControllerObject;
	ProductController productControllerObject;
	InventoryController inventoryController;
	OrderDetailsDao orderDetailsDataAccessObject=new OrderDetailsDao(); 
	InputStreamReader inputStreamReader=new InputStreamReader(System.in);
	BufferedReader bufferReader=new BufferedReader(inputStreamReader);
	public static List<OrderDetails> orderDetailsList=new ArrayList<>();
	

	Orders order;
	Products product;
	
	public void putOrderDetailsToArray() {
		orderDetailsDataAccessObject.putOrderDetailsToArray();
	}
	
	public void addOrderDetails() {
		productControllerObject=new ProductController();
		
	try {
				
			System.out.print("Enter order detail id: ");
			int id=Integer.parseInt(bufferReader.readLine());
			System.out.println();
			System.out.print("Enter Order id: ");
			int orderId=Integer.parseInt(bufferReader.readLine());
			System.out.println();
			System.out.print("Enter Product id: ");
			int productId=Integer.parseInt(bufferReader.readLine());
			System.out.println();
			System.out.print("Enter Quantity:");
			int quantity=Integer.parseInt(bufferReader.readLine());
			System.out.println();
			
			inventoryController=new InventoryController();
			//to check if product quantity is available  
			if(inventoryController.isProductAvailable(productId, quantity)) {
				
				if(id<0||id==0||orderId<0||orderId==0||productId<0||productId==0||quantity<0||quantity==0) {
					throw new IncompleteOrderException();
				}
				
				for(Orders o1:OrderController.orderList) {
					if(o1.getOrderId()==orderId) {
						order=o1;
						break;
					}
				}
				for(Products p1:productControllerObject.getProductList()) {
					if(p1.getProductId()==productId) {
						product=p1;
						break;
					}
				}
				//constructor calling
				orderDetail=new OrderDetails(id,order,product,quantity);
				boolean containsObject = orderDetailsList.stream()
		                .anyMatch(obj -> obj.getOrderDetailsid()==orderDetail.getOrderDetailsid());
				
				if(containsObject) {
					throw new DuplicateDataException();
				}
				
				orderDetailsList.add(orderDetail);
				orderDetailsDataAccessObject.insertOrderDetails(orderDetail);
				inventoryController.removeFromInvenory(productId, quantity);
				inventoryController.putInventoryToArray();
				
			}else {
				 throw new InsufficientStockException();
			 }
			
			}catch(IOException e) {
				e.printStackTrace();
			}catch(InsufficientStockException e) {
				System.err.println(e);
			}
			catch(IncompleteOrderException e) {
				System.err.println(e);
			} catch (DuplicateDataException e) {
				// TODO Auto-generated catch block
				System.err.println(e);
			}
	
	}
	
	public void calculateSubTotal(int odid) { 
		
		orderDetailsDataAccessObject.calculateSubTotal(odid);
	}
//	
		public void getOrderDetailInfo(int odid) {
			
//			for(OrderDetails od:odr) {
//				if(od.getOrderDetailsid()==odid) {
//					System.out.println(od);
//					break;
//				}
//			}
			
			orderDetailsDataAccessObject.getOrderDetailInfo(odid);
			
		}
		
		public void getAllOrderDetailInfo() {
			orderDetailsDataAccessObject.getAllOrderDetailInfo();
		}
		
		public void updateQuantity(int odid,int quant) {
			for(OrderDetails o:OrderDetailsController.orderDetailsList) {
				if(o.getOrderDetailsid()==odid) {
					o.setQuantity(quant);
					break;
				}
			}
			orderDetailsDataAccessObject.updateQuantity(odid, quant);
			System.out.println("Quantity updated!!");
		}
	
		public void AddDiscound(int odid,double discount) {
//			for(OrderDetails o:OrderDetailsController.odr) {
//				if(o.getOrderDetailsid()==odid) {
//					o.getOrder().setTotalAmount((o.getOrder().getTotalAmount())-(o.getOrder().getTotalAmount())*discount);
//					break;
//				}
//			}
			orderDetailsDataAccessObject.addDiscount(odid, discount);
			orderDetailsDataAccessObject.putOrderDetailsToArray();
		}
	
		public List<OrderDetails> getOrderDetailsList(){
			return orderDetailsList;
		}
}
