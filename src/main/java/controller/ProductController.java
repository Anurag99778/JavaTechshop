package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.ProductDao;
import entities.Products;
import exceptions.DuplicateDataException;
import exceptions.InvalidDataException;

public class ProductController {
	Products product=new Products();
	InventoryController inventoryControllerObject;
	ProductDao productDataAccessObject=new ProductDao();
	InputStreamReader inputStreamReader=new InputStreamReader(System.in);
	BufferedReader bufferReader=new BufferedReader(inputStreamReader);
	public static List<Products> productsList=new ArrayList<>();
	
	public void putProductsToArray() {
		productDataAccessObject.putProductsToArray();
	}
	
	public void add_Product() {
		try {
			
			System.out.print("Enter product id: ");
			int id=Integer.parseInt(bufferReader.readLine());
			System.out.println();
			System.out.print("Enter Product Name: ");
			String n=bufferReader.readLine();
			System.out.println();
			System.out.print("Enter Description: ");
			String des=bufferReader.readLine();
			System.out.println();
			System.out.print("Enter Product Price:");
			Double pri=Double.parseDouble(bufferReader.readLine());
			
			product=new Products(id,n,des,pri);
			boolean containsObject = productsList.stream()
	                .anyMatch(obj -> obj.getProductId()==product.getProductId());
			
			if(containsObject) {
				throw new DuplicateDataException();
			}
			productsList.add(product);
			productDataAccessObject.insertProduct(product);
			System.out.println("Product Added Succesfully!!");
			
		}catch(DuplicateDataException e) {
			System.err.println(e);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getProductDetails(int pid) {
		
//		for(Products pp:pl) {
//			if(pp.getProductId()==pid) {
//				System.out.println("Product ID: " + pp.getProductId());
//		        System.out.println("Name: " + pp.getProductName());
//		        System.out.println("Description: " + pp.getDescription());
//		        System.out.println("Price: " + pp.getPrice());
//		        break;
//			}
//		}
		productDataAccessObject.getProductDetails(pid);
	}
	
	public void searchProduct(String name) {
		productDataAccessObject.searchProduct(name);
	}
	
	public void updateProductInfo(int pid) {
		char ch;
		Scanner sc=new Scanner(System.in);
		double d=0.0;
		StringBuffer str=new StringBuffer("");
		try {
			for(Products pp:productsList) {
				if(pp.getProductId()==pid) {
					System.out.println("Update description? y/n");
					ch=sc.nextLine().charAt(0);
					if(ch=='y') {
						System.out.println("Enter description: ");
						str=str.append(bufferReader.readLine());
						if(str.toString().equals(""))
							throw new InvalidDataException();
						pp.setDescription(str.toString());
					}
					System.out.println("Update product Name? y/n");
					ch=sc.nextLine().charAt(0);
					if(ch=='y') {
						System.out.println("Enter new name: ");
						str.setLength(0);
						str=str.append(bufferReader.readLine());
						if(str.toString().equals(""))
							throw new InvalidDataException();
						pp.setProductName(str.toString());
					}
					System.out.println("Update price? y/n");
					ch=sc.nextLine().charAt(0);
					if(ch=='y') {
						System.out.println("Enter price: ");
						str.setLength(0);
						str=str.append(bufferReader.readLine());
						if(str.toString().equals(""))
							throw new InvalidDataException();
						d=Double.parseDouble(str.toString());
						pp.setPrice(d);
					}
					productDataAccessObject.updateProductDetails(pp);
					productDataAccessObject.putProductsToArray();
					System.out.println("Product Data Updated Successfully!");
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
	}
	
	public void isProductInStock(int pid) {
//		ic=new InventoryController();
//		for(Inventory i:ic.getInventoryList()) {
//			if(i.getProduct().getProductId()==pid) {
//				if(i.getQuantityInStock()>0) {
//					return true;
//				}else {
//					return false;
//				}
//			}
//		}
//		return false;
		productDataAccessObject.isProductInStock(pid);
	}
	
	//show all products
	public void showAllProducts() {
		productDataAccessObject.showAllProducts();
	}
	
	public List<Products> getProductList() {
		return productsList;
	}

}
