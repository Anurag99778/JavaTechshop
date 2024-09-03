package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.CustomerDao;
import entities.Customers;
import exceptions.DuplicateDataException;
import exceptions.InvalidDataException;

public class CustomerController {
	Customers customer;
	OrderController orderControllerObject = new OrderController();
	CustomerDao customerDataAccessObject=new CustomerDao();
	public static List<Customers> customerList=new ArrayList<>();
	InputStreamReader inputStreamReader=new InputStreamReader(System.in);
	BufferedReader bufferReader=new BufferedReader(inputStreamReader);
	
	public void putCustomersToArray() {
		customerDataAccessObject.putCustomersToArray();
	}
	
	public void add_Customer() {
	try {
		System.out.print("Enter Customer id: ");
		int id=Integer.parseInt(bufferReader.readLine());
		System.out.println();
		System.out.print("Enter Customer First Name: ");
		String fn=bufferReader.readLine();
		System.out.println();
		System.out.print("Enter Customer Last Name: ");
		String ln=bufferReader.readLine();
		System.out.println();
		System.out.print("Enter Customer Email:");
		String em=bufferReader.readLine();
		if(!em.contains("@gmail.com")) {
			throw new InvalidDataException();
		}
		System.out.println();
		System.out.print("Enter Customer Phone: ");
		String phno=bufferReader.readLine();
		System.out.println();
		System.out.print("Enter Customer Address: ");
		String ad=bufferReader.readLine();
		
		if(id<0||fn==""||ln==""||em==""||phno==""||ad=="") {
			throw new InvalidDataException();
		}
		String check = fn.replaceAll("\\d", "");
		if(check.length() != fn.length()) {
			throw new InvalidDataException();
		}
		check = ln.replaceAll("\\d", "");
		if(check.length() != ln.length()) {
			throw new InvalidDataException();
		}
		customer=new Customers(id,fn,ln,em,phno,ad);
		boolean containsObject = customerList.stream()
		        .anyMatch(obj -> obj.getCustomerId()==customer.getCustomerId());
		
		if(containsObject) {
			throw new DuplicateDataException();
		}
		customerList.add(customer);
		customerDataAccessObject.insertCustomer(customer);
		System.out.println("Customer Added Succesfully!!");
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		catch(InvalidDataException e) {
			System.err.println(e);
		} catch (DuplicateDataException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
	}
	
	
	public void CalculateTotalOrders(int id) {
		// TODO Auto-generated method stub
//		int total=0;
//		for(Orders o:oc.getOrderList()) {
//			if(o.getCustomer().getCustomerId()==id) {
//				total++;
//			}
//		}
//		return total;
		customerDataAccessObject.calculateTotalOrders(id);
	}

	
	
	public void getCustomerDetails(int id) {
		// TODO Auto-generated method stub
		
		customerDataAccessObject.displayCustomer(id);
		for(Customers c:customerList) {
			if(c.getCustomerId()==id) {
				if(customerList.size()!=0) {
					System.out.println(c);
					break;
				}else {
					System.out.println("No Customer Record Exists!!");
				}
				customerDataAccessObject.displayCustomer(id);
			}
		}
	}

	public void displayAllCustomers() {
		//System.out.println(cu);
		customerDataAccessObject.displayAllCustomers();
	}
	
	public void updateCustomerInfo(int id) {
		// TODO Auto-generated method stub
		char ch;
		StringBuffer temp=new StringBuffer("");
		Scanner sc=new  Scanner(System.in);
		
		try {
			
			for(Customers t1:customerList) {
				if(t1.getCustomerId()==id) {
					System.out.println("Update Email? y/n");
					ch=sc.nextLine().charAt(0);
					if(ch=='y') {
						System.out.println("Enter email: ");
						temp=temp.append(bufferReader.readLine());
						if(!temp.toString().contains("@gmail.com")||temp.toString().equals("")) {
							throw new InvalidDataException();
						}
						t1.setEmail(temp.toString());
					}
					System.out.println("Update phone no? y/n");
					ch=sc.nextLine().charAt(0);
					if(ch=='y') {
						System.out.println("Enter phone: ");
						temp.setLength(0);
						temp=temp.append(bufferReader.readLine());
						if(temp.toString().equals("")) {
							throw new InvalidDataException();
						}
						t1.setPhone(temp.toString());
					}
					System.out.println("Update Address? y/n");
					ch=sc.nextLine().charAt(0);
					if(ch=='y') {
						System.out.println("Enter address: ");
						temp.setLength(0);
						temp=temp.append(bufferReader.readLine());
						if(temp.toString().equals("")) {
							throw new InvalidDataException();
						}
						t1.setAddress(temp.toString());
					}
					
					customerDataAccessObject.updateCustomer(id,t1.getFirstName(),t1.getLastName(),t1.getEmail(),t1.getPhone(),t1.getAddress());
					System.out.println("Data Updated Successfully!");
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
	
	public List<Customers> getCustomerList(){
		return customerList;
	}
}
