package database;

import models.Product;
import views.Warn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CartDb {

    private final Connection conn;

    public CartDb() {
        this.conn = DatabaseHandler.getConnection();
    }

    public ArrayList<Product> getCartItems(String username) {
        String sql = "SELECT * FROM cart WHERE username = ?";
        ArrayList<Product> products = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
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

    public void addProductToCart(String username, Product product) {
        String sql = "INSERT INTO cart(username, product_key, product_name, product_price) VALUES(?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, product.getKey());
            pstmt.setString(3, product.getProductName());
            pstmt.setInt(4, product.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
    }

    public void emptyCart(String username) {
        String sql = "DELETE FROM cart WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
    }
}
