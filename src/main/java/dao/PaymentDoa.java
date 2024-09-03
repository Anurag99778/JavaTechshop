package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBConnection;
public class PaymentDoa {
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet resultSet;

    // Other methods in your PaymentDao class...

    public void processPayment(int orderId, String paymentMethod, double amount) {
        if (isValidPaymentMethod(paymentMethod) && amount > 0) {
            boolean paymentSuccessful = processPaymentExternally(paymentMethod, amount);

            if (paymentSuccessful) {
                updatePaymentStatus(orderId, "Paid");
                System.out.println("Payment successful for Order ID: " + orderId);
            } else {
                System.out.println("Payment failed for Order ID: " + orderId);
            }
        } else {
            System.out.println("Invalid payment information. Payment failed for Order ID: " + orderId);
        }
    }
    public void updatePaymentStatus(int orderId, String status) {
        try {
            connection = DBConnection.getMyDBConnection();
            ps = connection.prepareStatement("update Orders set payment_status = ? where orderid = ?");
            ps.setString(1, status);
            ps.setInt(2, orderId);

            int noofrows = ps.executeUpdate();
            System.out.println(noofrows + " Payment Status Updated successfully !!!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPaymentDetails(int orderId) {
        try {
            connection = DBConnection.getMyDBConnection();
            ps = connection.prepareStatement("select * from Orders where orderid = ?");
            ps.setInt(1, orderId);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                System.out.println("Payment Details for Order ID: " + orderId);
                System.out.println("Payment Status: " + resultSet.getString("payment_status"));
                System.out.println("Payment Date: " + resultSet.getDate("payment_date"));
                System.out.println("Payment Amount: " + resultSet.getDouble("payment_amount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Other methods in your PaymentDao class...

    private boolean isValidPaymentMethod(String paymentMethod) {
        return paymentMethod != null && !paymentMethod.isEmpty();
    }

    private boolean processPaymentExternally(String paymentMethod, double amount) {
        // Simulate external payment processing logic
        // In a real-world scenario, integrate with a payment gateway or service
        System.out.println("Processing payment using " + paymentMethod + "...");
        // Simulate success for demonstration purposes
        return true;
    }
}
