package it.unibo.ristoDB.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public ObservableList<Product> viewProductsByCategory(Category category) {
        final String query = "SELECT * from Prodotti" +
                            "WHERE Prodotti.idCategoria = " + category.getId();
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
    public ObservableList<OrderDetail> viewOrderDetail(Table table) {
        final String query = "SELECT * from Dettaglio_Ordini " +
                            "WHERE Ordini.Numero_Tavolo = " + table.getNumber() +
                            "AND Dettaglio_Ordini.ID_Ordine = Risultato.ID_Ordine";/*********** */
        try (Statement statement = connection.createStatement()) {
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
    public void removeEmployee(String name, String surname) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeEmployee'");
    }

    @Override
    public void addEmployee(String name, String surname) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addEmployee'");
    }

    @Override
    public void addCategory(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCategory'");
    }

    @Override
    public void addCategory(String name, float price, Category category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCategory'");
    }

    @Override
    public void removeProduct(String productName, String CategoryName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeProduct'");
    }

    @Override
    public Map<String, Float> viewBestEmployee() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewBestEmployee'");
    }

    @Override
    public ObservableList<Product> viewBestFiveProducts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewBestFiveProducts'");
    }

    @Override
    public Date viewBusyMoment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewBusyMoment'");
    }
    
}
