package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import dao.InventoryDao;
import entities.Inventory;
import entities.Products;
import exceptions.DuplicateDataException;
import exceptions.InsufficientStockException;

public class InventoryController {
	ProductController productControllerObject = new ProductController();
	Inventory inventoryObject;
	InventoryDao inventoryDataAccessObject=new InventoryDao();
	InputStreamReader inputStreamReader=new InputStreamReader(System.in);
	BufferedReader bufferReader=new BufferedReader(inputStreamReader);
	public static List<Inventory> inventoryList=new ArrayList<>();
	public static Map<Integer, Inventory> sortedList=new TreeMap<>();
	
	public void putInventoryToArray() {
		inventoryDataAccessObject.putInventoryToArray();
		for(Inventory i:inventoryList) {
			sortedList.put(i.getProduct().getProductId(), i);
		}
	}
	
	public Products returnProductObject(int pid) {
		List<Products> pl = productControllerObject.getProductList();
		for(Products e:pl) {
			if(e.getProductId()==pid) {
				return e;
			}
		}
		return null;
	}
	
	public void SortedInventoryList() {
		
		System.out.println(sortedList);
	}
	
	public void addToInventory() {
		try {
			System.out.print("Enter Inventory Id: ");
			int i=Integer.parseInt(bufferReader.readLine());
			System.out.println();
			
			System.out.print("Enter Product Id: ");
			int pid=Integer.parseInt(bufferReader.readLine());
			System.out.println();
			
			System.out.print("Enter Quantity in stock: ");
			int quanti=Integer.parseInt(bufferReader.readLine());
			System.out.println();
			
			System.out.print("Enter Last Stock Update: ");
			LocalDate lsu=LocalDate.parse(bufferReader.readLine());
			System.out.println();
		
			InventoryController ic=new InventoryController();
			inventoryObject=new Inventory(i,ic.returnProductObject(pid),quanti,lsu);
			
			boolean containsObject = inventoryList.stream()
	                .anyMatch(obj -> obj.getInventoryId()==inventoryObject.getInventoryId());
			
			if(containsObject) {
				throw new DuplicateDataException();
			}
			inventoryList.add(inventoryObject);
			//inventoryDataAccessObject.addToInventory(inventoryObject);
		
		}catch(IOException e) {
			e.printStackTrace();
		} catch (DuplicateDataException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
	}
	
	public void getProduct(int pid) {
//		for(Inventory i:il) {
//			if(i.getProduct().getProductId()==pid) {
//				System.out.println(i);
//			}
//		}
		
		inventoryDataAccessObject.getProduct(pid);
	}
	
	public void getQuantityInStock(int pid) {
//		for(Inventory i:il) {
//			if(i.getProduct().getProductId()==pid) {
//				System.out.println("Quantity for product:"+i.getProduct().getProductName()+" is "+i.getQuantityInStock());
//			}
//		}
		inventoryDataAccessObject.getQuantityInStock(pid);
	}
	
	public void removeFromInvenory(int id,int q) {
//		for(Inventory i:il) {
//			if(i.getInventoryId()==id) {
//				i.setQuantityInStock(Math.abs(i.getQuantityInStock()-q));
//				System.out.println(i.getQuantityInStock()+" Product Quantity Removed from stock!!");
//				break;
//			}
//		}
		inventoryDataAccessObject.removeFromInventory(id, q);
	}
	
	public void updateStockQuantity(int id,int q) {
//		for(Inventory i:il) {
//			if(i.getInventoryId()==id) {
//				i.setQuantityInStock(q);
//				System.out.println("Quantity in Stock Updated!!");
//				i.setLastStockUpdate(LocalDate.now());
//				break;
//			}
//		}
		inventoryDataAccessObject.updateStockQuantity(id, q);
	}
	
	public boolean isProductAvailable(int id,int qtc) {
		boolean isAvailable=false;
//		for(Inventory i:il) {
//			if(i.getInventoryId()==id) {
//				if(i.getQuantityInStock()-qtc >= 0) {
//					System.out.println("Product in stock!");
//				}else {
//					System.out.println("Product out of stock!");
//				}
//			}
//		}
		try {
			if(inventoryDataAccessObject.isProductAvailable(id, qtc)) {
				System.out.println("Product is in Stock!!");
				isAvailable=true;
			}else {
				isAvailable=false;
				throw new InsufficientStockException();
			}
		}catch(InsufficientStockException e) {
			System.err.println(e);
		}
		return isAvailable;
	}
	
	
	public void getInventoryValue() {
//		double val=j.getProduct().getPrice()*j.getQuantityInStock();
//		return val;
		inventoryDataAccessObject.getInventoryValue();
	}
	
	
	public void listLowStockProducts(int threshold) {
	
//		for(Inventory e:il) {
//			if(e.getQuantityInStock()<threshold) {
//				System.out.println(e);
//			}
//		}
		inventoryDataAccessObject.listLowStockProducts(threshold);
	}
	
	public void listOutOfStockProducts() {
//		for(Inventory e:il) {
//			if(e.getQuantityInStock()==0) {
//				System.out.println(e);
//			}
//		}
		inventoryDataAccessObject.listOutOfStockProducts();
	}
	
	public void listAllProducts() {
//		for(Inventory e:il) {
//			System.out.println(e);
//		}
		inventoryDataAccessObject.listAllInventoryProducts();
	}
	
	public List<Inventory> getInventoryList(){
		return inventoryList;
	}
}
