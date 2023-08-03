package it.unibo.ristoDB.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
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
        final String query = "SELECT * from Categorie";
        try (Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<Category> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Category(result.getInt("id"), result.getString("nome"), new ArrayList<Product>()));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<Table> viewAllTables() {
        final String query = "SELECT * from Tavoli";
        try (Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<Table> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Table(result.getInt("numero"), result.getBoolean("occupato"), result.getInt("Max_Pers")));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<Product> viewAllProducts() {
        final String query = "SELECT * from Prodotti";
        try (Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<Product> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Product(result.getInt("id"), result.getString("nome"), result.getFloat("prezzo"), result.getInt("ID_Categoria")));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<Product> viewProductsByCategory(final int categoryId) {
        final String query = "SELECT * from Prodotti" +
                            "WHERE Prodotti.idCategoria = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<Product> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new Product(result.getInt("id"), result.getString("nome"), result.getFloat("prezzo"), result.getInt("ID_Categoria")));
            }
            return list;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ObservableList<OrderDetail> viewOrderDetail(final int tableNumber) {
        final String query = "SELECT * from Dettagli_Ordini " +
                         "JOIN Ordini ON Dettagli_Ordini.ID_Ordine = Ordini.ID " +
                         "JOIN Tavoli ON Tavoli.Numero_Tavolo = Ordini.Numero_Tavolo " +
                         "WHERE Tavoli.Numero_Tavolo = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tableNumber);
            final ResultSet result = statement.executeQuery(query);
            final ObservableList<OrderDetail> list = FXCollections.observableArrayList();
            while (result.next()) {
                list.add(new OrderDetail(result.getInt("ID_Ordine"), new HashMap<>()));
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
        final String query = "DELETE FROM Camerireri WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean checkDependencies(final int employeeId) {
        final String query = "SELECT * FROM Fattura WHERE id_fornitore = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, employeeId);
            return statement.executeQuery().next();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

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

    @Override
    public void addEmployee(final String firstName, final String lastName, final String username, final String password) {
        final String query = "INSERT INTO Camerireri "
                + "(nome, cognome, username, password) "
                + " VALUES (?,?,?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.executeUpdate();
        } catch (final SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException(e);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void addCategory(String categoryName) {
        final String query = "INSERT INTO Categorie "
                + "(nome) "
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
        final String query = "INSERT INTO Prodotti "
                + "(nome, prezzo, ID_Categoria) "
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
        final String query = "DELETE FROM Prodotti WHERE id = ?";
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
    public Date viewBusyMoment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewBusyMoment'");
    }
    
}
