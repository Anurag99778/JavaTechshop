package controller;

import java.util.List;

import entities.OrderDetails;

public interface IOrderDetails {
	public void addOrderDetails();
	
	public void calculateSubTotal(int odid);
		
	public void getOrderDetailInfo(int odid);
		
	public void updateQuantity(int odid,int quant);
	
	public void AddDiscound(int odid,double discount);
	
	public List<OrderDetails> getOrderDetailsList();
}
