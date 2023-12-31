package it.unibo.ristoDB.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

import it.unibo.ristoDB.db.Category;
import it.unibo.ristoDB.db.OrderDetail;
import it.unibo.ristoDB.db.Product;
import it.unibo.ristoDB.db.Table;
import it.unibo.ristoDB.db.User;
import it.unibo.ristoDB.view.BestProducts;
import it.unibo.ristoDB.view.ReceiptsOrder;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

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
    ObservableList<Product> viewProductsByCategory(String categoryName);

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
    boolean addEmployee(String name, String surname, String username, String password);

    /**
     * Add Category
     */
    void addCategory(String name);

    /**
     * Add product
     */
    void addProduct(String name, float price, String categoryName);

    /**
     * Remove Product
     */
    void removeProduct(int productId);

    /**
     * Add an order detail
     */
    void addOrderDetails(int productId, int quantity);

    /**
     * Show total of the receipt
     */
    float showReceiptTotal(int tableNumber);

    /**
     * Add new receipt
     */
    void addReceipt(int tableNumber);

    /**
     * Show total of the receipt
     */
    ObservableList<ReceiptsOrder> showReceiptOrder(int tableNumber);

    /**
     * 
     * @return a map with the name of the best employee and how much he sells 
     */
    Map<String,Float> viewBestEmployee(Date date);

    /**
     * 
     * @return a list with best seller products
     */
    ObservableList<BestProducts> viewBestSellingProducts();

    /**
     * 
     * @param date to find
     * @return avarage expense of that day
     */
    Float viewAvarageExpense(Date date);
    
    /**
     * @param date to find
     * @return total of covered of the day
     */
    int viewTotalCovered(Date date);

    /***
     * displays all days where there is at least one order
     * @return a list of dates
     */
    ObservableList<Date> viewAllDate();

    boolean findUser(String username);

    boolean findShift(java.sql.Date date, String dayMoment);

    void associateEmployeeShift(java.sql.Date date, String dayMoment, String user);

    boolean checkUser(String username, String password);

    ObservableList<User> viewEmployeesOnShift(java.sql.Date date, String dayMoment);

    ObservableList<User> viewAllUsers();

    void setSelectedTableNumber(int tableNumber);

    void setUsername(String username);

    ObservableList<Table> viewOpenedTables();

    void closeTable(int table);

    boolean verifyCovered(int tableNumber);

    Float viewAvaragePeoplePerTable(Date date);

    ObservableList<Date> getAllWorkshift();

    
}
