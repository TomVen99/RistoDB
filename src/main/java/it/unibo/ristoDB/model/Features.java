package it.unibo.ristoDB.model;

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
    ObservableList<Product> viewProductsByCategory(Category category);

    /**
     * @return a list of Products
     */
    ObservableList<OrderDetail> viewOrderDetail(Table table);

    /**
     * Remove an Employee
     */
    void removeEmployee(String name, String surname);

    /**
     * Add an Employee
     */
    void addEmployee(String name, String surname);

    /**
     * Add Category
     */
    void addCategory(String name);

    /**
     * Add product
     */
    void addCategory(String name, float price, Category category);

    /**
     * Remove Product
     */
    void removeProduct(String productName, String CategoryName);

    /**
     * 
     * @return a map with the name of the best employee and how much he sells 
     */
    Map<String,Float> viewBestEmployee();

    /**
     * 
     * @return a list with best seller products
     */
    ObservableList<Product> viewBestFiveProducts();

    /**
     * 
     * @return when resturant is busiest
     */
    Date viewBusyMoment();
}
