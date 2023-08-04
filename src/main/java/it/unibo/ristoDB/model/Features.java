package it.unibo.ristoDB.model;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

import it.unibo.ristoDB.db.Category;
import it.unibo.ristoDB.db.OrderDetail;
import it.unibo.ristoDB.db.Product;
import it.unibo.ristoDB.db.Table;
import javafx.collections.ObservableList;

public interface Features {

    /**
     * @return a list of Categories
     */
    ObservableList<Category> viewAllCategory();

    /**
     * @return a list of Tables
     */
    ObservableList<Table> viewAllTables();

    /**
     * @return a list of Products
     */
    ObservableList<Product> viewAllProducts();

    /**
     * 
     * @param category to filter products
     * @return a list of Products appertain to the category
     */
    ObservableList<Product> viewProductsByCategory(int categoryId);

    /**
     * @return a list of Products
     */
    ObservableList<OrderDetail> viewOrderDetail(int tableNumber);

    /**
     * Remove an Employee
     */
    void removeEmployee(int employeeId);

    /**
     * Add an Employee
     */
    void addEmployee(String name, String surname, String username, String password);

    /**
     * Add Category
     */
    void addCategory(String name);

    /**
     * Add product
     */
    void addProduct(String name, float price, int categoryId);

    /**
     * Remove Product
     */
    void removeProduct(int productId);

    /**
     * Add an order detail
     */
    void addOrderDetails(int productId, int quantity);

    /**
     * Add an order
     */
    void addOrder(Date date, Time time, int tableNumber, int employeeId);

    /**
     * Show total of the receipt
     */
    void showReceipt(int tableNumber);

    /**
     * 
     * @return a map with the name of the best employee and how much he sells 
     */
    Map<String,Float> viewBestEmployee();

    /**
     * 
     * @return a list with best seller products
     */
    ObservableList<Product> viewBestSellingProducts();

    /**
     * 
     * @return when resturant is busiest
     */
    Date viewBusyMoment();
}
