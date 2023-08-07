package it.unibo.ristoDB.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.unibo.ristoDB.db.Category;
import it.unibo.ristoDB.db.OrderDetail;
import it.unibo.ristoDB.db.Product;
import it.unibo.ristoDB.db.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FeaturesImpl implements Features{

    private final Connection connection;

    public FeaturesImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ObservableList<Category> viewAllCategory() {
        final String query = "SELECT * from Categories";
        try (Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<Category> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Category(result.getInt("ID"), result.getString("Name"), new ArrayList<Product>()));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<Table> viewAllTables() {
        final String query = "SELECT * from Tables";
        try (Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<Table> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Table(result.getInt("Number"), result.getBoolean("Busy"), result.getInt("Max_People")));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<Product> viewAllProducts() {
        final String query = "SELECT * from Products";
        try (Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<Product> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Product(result.getInt("ID"), result.getString("Name"), result.getFloat("Price"), result.getInt("Category_ID")));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<Product> viewProductsByCategory(final int categoryId) {
        final String query = "SELECT * from Products" +
                            "WHERE Products.Category_ID = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<Product> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Product(result.getInt("ID"), result.getString("Name"), result.getFloat("Price"), result.getInt("Category_ID")));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<OrderDetail> viewOrderDetail(final int tableNumber) {
        final String query = "SELECT * from Orders_Details " +
                         "JOIN Orders ON Orders_Details.Order_ID = Orders.ID " +
                         "JOIN Tables ON Tables.Table_Number = Orders.Table_Number " +
                         "WHERE Tables.Table_Number = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tableNumber);
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<OrderDetail> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new OrderDetail(result.getInt("Order_ID"), new HashMap<>()));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void removeEmployee(final int employeeId) {
        if (employeeId == 1) {
            throw new IllegalArgumentException("cannot remove id = 1 ");
        }
        if (checkDependencies(employeeId)) {
            handleDependencies(employeeId);
        }
        final String query = "DELETE FROM Employee WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /******* */
    private boolean checkDependencies(final int employeeId) {
        final String query = "SELECT * FROM Fattura WHERE id_fornitore = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            return statement.executeQuery().next();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /****** */
    private void handleDependencies(final int employeeId) {
        final String query = "UPDATE Fattura SET id_fornitore = 1 "
                + "WHERE id_fornitore = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    /*****Devo verificare se l'user va bene e non è duplicato */
    @Override
    public boolean addEmployee(final String firstName, final String lastName, final String username, final String password) {
        if(userDontExist(username)){
            final String queryEmployee = "INSERT INTO Employee "
                    + "(Name, LastName) "
                    + " VALUES (?,?)";
            final String queryUser = "INSERT INTO Users "
                    + "(Username, Password) "
                    + " VALUES (?,?)";
            try {
                PreparedStatement statement = this.connection.prepareStatement(queryEmployee);
                PreparedStatement statement2 = this.connection.prepareStatement(queryUser);
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement2.setString(1, username);
                statement2.setString(2, password);
                statement.executeUpdate();
                statement2.executeUpdate();
                return true;
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
        }else {
            return false;
        }

    }
    /**
     * @param username user to find
     * @return true if username don't exist
     */
    private boolean userDontExist(String username) {
        final String query = "SELECT * from USERS "
                    + "WHERE users.username = ? ";
            try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, username);
                final ResultSet result = statement.executeQuery(query);
                return !result.next();
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }

    @Override
    public void addCategory(String categoryName) {
        final String query = "INSERT INTO Categories "
                + "(Description) "
                + " VALUES (?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, categoryName);
            statement.executeUpdate();
        } catch (final SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException(e);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void addProduct(String name, float price, int categoryId) {
        final String query = "INSERT INTO Products "
                + "(Name, Price, Category_ID) "
                + " VALUES (?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setFloat(2, price);
            statement.setInt(3, categoryId);
            statement.executeUpdate();
        } catch (final SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException(e);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void removeProduct(final int productId) {
        if (checkDependencies(productId)) {
            handleDependencies(productId);
        }
        final String query = "DELETE FROM Products WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Map<String, Float> viewBestEmployee() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewBestEmployee'");
    }

    @Override
    public ObservableList<Product> viewBestSellingProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewBestFiveProducts'");
    }

    @Override
    public Date viewBusyDay() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewBusyMoment'");
    }

    @Override
    public void addOrderDetails(int productId, int quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addOrderDetails'");
    }

    @Override
    public void showReceipt(int tableNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showReceipt'");
    }

    @Override
    public void addOrder(Date date, Time time, int tableNumber, int employeeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addOrder'");
    }

    @Override
    public Float viewAvarageExpense(Date date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewAvarageExpense'");
    }

    @Override
    public Integer viewTotalCovered(Date date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewTotalCovered'");
    }

    @Override
    public ObservableList<Date> viewAllDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewAllDate'");
    }
    
}
