package controller;

public interface ICustomer {
	public void putCustomersToArray();
	
	//add customer
	public void add_Customer();
	
	//Calculates the total number of orders placed by this customer
	public void CalculateTotalOrders(int id);
	
	//Retrieves and displays detailed information about the customer.
	public void getCustomerDetails(int id);
	
	//: Allows the customer to update their information.
	public void updateCustomerInfo(int id);
}
