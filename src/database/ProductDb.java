package database;

import models.Product;
import views.Warn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDb {

    final private Connection conn;

    public ProductDb() {
        this.conn = DatabaseHandler.getConnection();
    }

    public void clearProducts() {
        final String sql = "DELETE FROM products";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
    }

    public void addProducts(ArrayList<Product> products) {
        String sql = "INSERT INTO products(product_key, product_name, product_price) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Product product : products) {
                pstmt.setInt(1, product.getKey());
                pstmt.setString(2, product.getProductName());
                pstmt.setInt(3, product.getPrice());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO products(product_key,product_name, product_price) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getKey());
            pstmt.setString(2, product.getProductName().toLowerCase());
            pstmt.setInt(3, product.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
    }

    public void modifyProduct(Product product) {
        String sql = "UPDATE products SET product_name = ?, product_price = ? WHERE product_key = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setInt(2, product.getPrice());
            pstmt.setInt(3, product.getKey());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
    }

    public void deleteProduct(Product product) {
        final String sql = "DELETE FROM products WHERE product_key = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getKey());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
    }

    public ArrayList<Product> getProducts() {
        String sql = "SELECT * FROM products ORDER BY product_key ASC";
        ArrayList<Product> products = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_key"),
                        rs.getString("product_name"),
                        rs.getInt("product_price")
                ));
            }
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
        return products;
    }

    public int getHighestProductId() {
        String sql = "SELECT MAX(product_key) as highest_id FROM products";
        int maxId = 1;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) maxId = rs.getInt("highest_id");
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
        return maxId;
    }

    public boolean isProductExist(String productName) {
        final String sql = "SELECT * FROM products WHERE product_name = ?";
        boolean isExist = false;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName);
            final ResultSet rs = pstmt.executeQuery();
            if (rs.next()) isExist = true;
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
        return isExist;
    }
}
