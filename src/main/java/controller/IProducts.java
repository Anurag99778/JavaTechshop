package controller;

import java.util.List;

import entities.Products;

public interface IProducts {
public void add_Product();
	
	public void getProductDetails(int pid);
	
	public void updateProductInfo(int pid);
	
	public void isProductInStock(int pid);
	
	public void showAllProducts();
	
	public List<Products> getProductList();
}
