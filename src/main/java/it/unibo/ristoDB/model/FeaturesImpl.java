package it.unibo.ristoDB.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.unibo.ristoDB.db.Category;
import it.unibo.ristoDB.db.OrderDetail;
import it.unibo.ristoDB.db.Product;
import it.unibo.ristoDB.db.Table;
import it.unibo.ristoDB.db.User;
import it.unibo.ristoDB.view.ReceiptsOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FeaturesImpl implements Features{

    private final Connection connection;
    private int tableNumber;
    private String username;
    private String covered = "coperto";

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
                //list.add(new OrderDetail(result.getInt("order_ID"), new HashMap<>()));
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
    public Map<String, Float> viewBestEmployee(Date date) {
        final String query = "select o.username, sum(od.quantity * p.price) as total from orders o"
            + " join orders_details od on o.date=od.date and o.time=od.time"
            + " join products p on od.product_ID = p.id"
            + " where o.date = ? and closing_time is NOT null"
            + " group by o.username"
            + " order by total DESC"
            + " limit 1";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setDate(1, fromDateToSQLDate(date));
                final ResultSet result = statement.executeQuery();
                final Map<String, Float> bestEmployee = new HashMap<>();
                if(result.next()) {
                    bestEmployee.put(result.getString("username"), result.getFloat("total"));
                }
                return bestEmployee;
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
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
        /*devo impostare il tavolo busy true, verifiare che ci siano i coperti */
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        java.sql.Time time = java.sql.Time.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        addOrder(date,time);
        System.out.println("qui sono arrivato *************************");
        final String query = "INSERT INTO Orders_Details "
                + " VALUES (?,?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            statement.setDate(2, date);
            statement.setTime(3, time);
            statement.setInt(4, quantity);
            statement.executeUpdate();
        } catch (final SQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            throw new IllegalArgumentException(e);
        } catch (final SQLException e) {
            System.out.println(e);
            throw new IllegalStateException(e);
        }
    }

    private void addOrder(java.sql.Date date, java.sql.Time time) {
        final String query = "INSERT INTO Orders "
                + " (date,time,username,number)"
                + " VALUES (?,?,?,?)";
        System.out.println("numero tavolo "+ tableNumber + "username "+ username);
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setDate(1, date);
            statement.setTime(2, time);
            statement.setString(3, username);
            statement.setInt(4, tableNumber);
            statement.executeUpdate();
        } catch (final SQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            throw new IllegalArgumentException(e);
        } catch (final SQLException e) {
            System.out.println(e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public float showReceiptTotal(int tableNumber) {
        final String query = "select SUM(price*quantity) as total from orders_details as od "
                    + "join products as p "
                    + "on p.id = od.product_id "
                    + "where od.date in (select o.date from Orders as o "
                        + "where o.number = ?) "
                    + "and od.time in (select o.time from Orders as o "
                        + "where o.number = ? and o.closing_time is NULL)";
            try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setInt(1, tableNumber);
                statement.setInt(2, tableNumber);
                final ResultSet result = statement.executeQuery();
                if(result.next()) {
                    return result.getFloat("total");
                }else {
                    System.out.println("Errore stampa del totale");
                    return 0;
                }
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }

    @Override
    public ObservableList<ReceiptsOrder> showReceiptOrder(int tableNumber) {
        final String query = "select p.name, p.price, SUM(od.quantity) as quantity from orders_details as od "
                    + "join products as p "
                    + "on p.id = od.product_id "
                    + "where od.date in (select o.date from Orders as o "
                        + "where o.number = ?) "
                    + "and od.time in (select o.time from Orders as o "
                        + "where o.number = ? and o.closing_time is NULL)"
                    + "group by p.name, p.price";
            try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setInt(1, tableNumber);
                statement.setInt(2, tableNumber);
                final ResultSet result = statement.executeQuery();
                final ObservableList<ReceiptsOrder> list = FXCollections.observableArrayList();
                while (result.next()) {
                    list.add(new ReceiptsOrder(result.getString("name"), result.getInt("quantity"), result.getFloat("price")));
                }
                System.out.println(list);
                return list;
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }

    @Override
    public Float viewAvarageExpense(Date date) {
        final String query = "select (select sum(quantity * price) from orders_details od"
                + " join products p on od.product_id = p.id"
                + " join orders o on o.date = od.date and o.time = od.time"
                + " where o.date = ?) / (select sum(quantity) as tot from orders_details od"
                + " join products p on od.product_ID = p.id"
                + " join orders o on od.date = o.date and o.time = od.time"
                + " where od.date = ? and p.name = ? and o.closing_time is not null) as media";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setDate(1, fromDateToSQLDate(date));
                statement.setDate(2, fromDateToSQLDate(date));
                statement.setString(3, covered);
                final ResultSet result = statement.executeQuery();
                result.next();
                return result.getFloat("media");
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }

    @Override
    public int viewTotalCovered(Date date) {
        final String query = "select od.date, sum(quantity) as tot from orders_details od"
                + " join products p on od.product_ID = p.id"
                + " join orders o on od.date = o.date and o.time = od.time"
                + " where od.date = ? and p.name = ? and o.closing_time is not null"
                + " group by od.date";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setDate(1, fromDateToSQLDate(date));
                statement.setString(2, covered);
                final ResultSet result = statement.executeQuery();
                result.next();
                return result.getInt("tot");
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }

    @Override
    public ObservableList<Date> viewAllDate() {
        final String query = "select distinct orders.date from orders";
        try (Statement statement = this.connection.createStatement()) {
                final ResultSet result = statement.executeQuery(query);
                final ObservableList<Date> list = FXCollections.observableArrayList();
                while (result.next()) {
                list.add(result.getDate("date")); 
            }
            return list;
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
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

    @Override
    public void setSelectedTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public ObservableList<Table> viewOpenedTables() {
        final String query = "select t.number, t.max_people from tables t"
            + " where t.number in (select t.number from tables t"
            + " join orders o on o.number = t.number"
            + " where o.closing_time is NULL)";
        try (Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<Table> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Table(
                    result.getInt("number"),
                    result.getInt("max_people")
                )); 
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void closeTable(int table) {
        final String query = "UPDATE Orders "
                + " SET closing_time = ?"
                + " WHERE number = ? and closing_time is null";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setTime(1, java.sql.Time.valueOf(LocalTime.now().truncatedTo(ChronoUnit.SECONDS)));
            statement.setInt(2, table);
            System.out.println("numero tavolo " + tableNumber);
            statement.executeUpdate();
            System.out.println("CHIUSO TAVOLO");
        } catch (final SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException(e);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean verifyCovered(int tableNumber) {
            final String query = "select t.number, t.max_people, SUM(od.quantity) as quantity from tables t" 
            + " join orders o on o.number = t.number" 
                    + " join orders_details od on o.date = od.date and o.time = od.time" 
                    + " join products p on od.product_ID = p.id" 
                    + " where p.name = ? and t.number = ? and o.closing_time is NULL" 
                    + " group by t.number, t.max_people" 
                    + " having SUM(od.quantity) <= t.max_people and SUM(od.quantity) > 0;";
            try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setString(1, covered);
                statement.setInt(2, tableNumber);
                final ResultSet result = statement.executeQuery();
                if(result.next()) {
                    return true;
                }
                return false;
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }    

    @Override
    public Float viewAvaragePeoplePerTable(Date date) {
        final String query = "select (select sum(quantity) as tot from orders_details od"
                + " join products p on od.product_ID = p.id"
                + " join orders o on od.date = o.date and o.time = od.time"
                + " where od.date = ? and p.name = ? and o.closing_time is not null)"
                + " /"
                + " (select sum(giriPerTavolo) as tot"
                    + " from( select count(distinct o.closing_time) as giriPerTavolo"
                    + " from orders_details od"
                    + " join orders o on o.date = od.date and o.time = od.time"
                + " where o.date = ?) as subQuery) as media";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
                statement.setDate(1, fromDateToSQLDate(date));
                statement.setString(2, covered);
                statement.setDate(3, fromDateToSQLDate(date));
                final ResultSet result = statement.executeQuery();
                result.next();
                return result.getFloat("media");
            } catch (final SQLIntegrityConstraintViolationException e) {
                throw new IllegalArgumentException(e);
            } catch (final SQLException e) {
                throw new IllegalStateException(e);
            }
    }

    /*private java.sql.Date fromStringToSQLDate (String dateString){
        try{
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = sdf1.parse(dateString);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            return sqlDate;
        }catch(ParseException e){
            System.out.println(e);
        }
    }*/

    private java.sql.Date fromDateToSQLDate (Date dateUtil){
        java.sql.Date sqlDate = new java.sql.Date(dateUtil.getTime());
        return sqlDate;
    }
}
