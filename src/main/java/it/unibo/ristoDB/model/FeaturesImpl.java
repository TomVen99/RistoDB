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
import it.unibo.ristoDB.db.User;
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
                list.add(new Category(result.getString("name")));
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
                list.add(new Table(result.getInt("number"), result.getInt("max_people")));
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
                list.add(new Product(result.getInt("ID"), result.getString("name"), result.getFloat("price"), result.getString("Inc_name")));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<Product> viewProductsByCategory(final String categoryName) {
        final String query = "SELECT * from Products" +
                            " WHERE Inc_name = ? ";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, categoryName);
            final ResultSet result = statement.executeQuery();
            final ObservableList<Product> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Product(result.getInt("ID"), result.getString("name"), result.getFloat("price"), result.getString("Inc_name")));
            }
            return list;
        } catch (final SQLException e) {
            System.out.println("********************" + e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<OrderDetail> viewOrderDetail(final int tableNumber) {
        final String query = "SELECT * from Orders_Details " +
                         "JOIN Orders ON Orders_Details.order_ID = Orders.ID " +
                         "JOIN Tables ON Tables.table_number = Orders.table_number " +
                         "WHERE Tables.table_number = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tableNumber);
            final ResultSet result = statement.executeQuery();
            final ObservableList<OrderDetail> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new OrderDetail(result.getInt("order_ID"), new HashMap<>()));
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
    public boolean addEmployee(final String name, final String lastName, final String username, final String password) {
        if(!findUser(username) && name != "" && lastName != "" && username != "" && password != ""){
            final String query = "INSERT INTO Users "
                    + "(username, password, name, lastname) "
                    + " VALUES (?,?,?,?)";
            try {
                PreparedStatement statement = this.connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);
                statement.setString(3, name);
                statement.setString(4, lastName);
                statement.executeUpdate();
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
    public boolean findUser(String username){
        final String query = "SELECT * from USERS "
                    + "WHERE users.username = ? ";
            try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, username);
                final ResultSet result = statement.executeQuery();
                return result.next();
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }

    @Override
    public void addCategory(String categoryName) {
        final String query = "INSERT INTO Categories "
                + "(name) "
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
    public void addProduct(String name, float price, String categoryName) {
        final String query = "INSERT INTO Products "
                + "(name, price, Inc_name) "
                + " VALUES (?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setFloat(2, price);
            statement.setString(3, categoryName);
            statement.executeUpdate();
        } catch (final SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException(e);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void removeProduct(final int productId) {
        /*if (checkDependencies(productId)) {
            handleDependencies(productId);
        }*/
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

    @Override
    public boolean findShift(java.sql.Date date, String dayMoment) {
        final String query = "SELECT * from WORKSHIFTS "
                    + "WHERE date = ? AND day_Moment = ?";
            try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setDate(1, date);
                statement.setString(2, dayMoment);
                final ResultSet result = statement.executeQuery();
                return result.next();
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }

    @Override
    public void associateEmployeeShift(java.sql.Date date, String dayMoment, String user) {
        final String query = "INSERT INTO Shifts_assignment "
                + " VALUES (?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, user);
            statement.setDate(2, date);
            statement.setString(3, dayMoment);
            statement.executeUpdate();
        } catch (final SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException(e);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean checkUser(String username, String password) {
        final String query = "SELECT * from USERS "
                    + "WHERE users.username = ? AND users.password = ?";
            try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                final ResultSet result = statement.executeQuery();
                return result.next();
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }

    @Override
    public ObservableList<User> viewEmployeesOnShift(java.sql.Date date, String dayMoment) {
        final String query = "SELECT u.username, u.name, u.lastname, u.password from Users as u "
            + "join shifts_assignment s on s.username = u.username "
            + "join workshifts w on w.date = s.date AND w.day_Moment = s.day_Moment "
            + "where s.date = ? and s.day_Moment = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setDate(1, date);
            statement.setString(2, dayMoment);
            final ResultSet result = statement.executeQuery();
            final ObservableList<User> data = FXCollections.observableArrayList();
            data.clear();
            while (result.next()) {
                data.add(new User(
                    result.getString("username"),
                        result.getString("password"),
                        result.getString("name"),
                        result.getString("lastname")
                )); 
            }
            return data;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<User> viewAllUsers() {
        final String query = "SELECT * from Users";
        try (Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<User> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new User(
                    result.getString("username"),
                        result.getString("password"),
                        result.getString("name"),
                        result.getString("lastname")
                )); 
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }    
}
