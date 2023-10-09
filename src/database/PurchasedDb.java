package database;

import models.Product;
import models.PurchasedLog;
import views.Warn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchasedDb {
    private final Connection conn;

    public PurchasedDb() {
        this.conn = DatabaseHandler.getConnection();
    }

    private ArrayList<String> getUniqueDates() {
        final String sql = "SELECT DISTINCT date FROM purchased";
        ArrayList<String> uniqueDates = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                uniqueDates.add(rs.getString("date"));
            }
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        }
        return uniqueDates;
    }

    public ArrayList<PurchasedLog> getPurchased(String username) {
        ArrayList<PurchasedLog> purchases = new ArrayList<>();
        final ArrayList<String> uniqueDates = getUniqueDates();

        for (String date : uniqueDates) {
            final String sql = "SELECT * FROM purchased WHERE date = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, date);
                ResultSet rs = pstmt.executeQuery();

                ArrayList<Product> products = new ArrayList<>();
                while (rs.next()) {
                    final Product product = new Product(
                            rs.getInt("product_key"),
                            rs.getString("product_name"),
                            rs.getInt("product_price")
                    );
                    products.add(product);
                }
                purchases.add(new PurchasedLog(username, products, date));
            } catch (SQLException e) {
                Warn.debugMessage(e.getMessage());
            }
        }
        DatabaseHandler.closeConnection();
        return purchases;
    }

    public void addPurchased(PurchasedLog purchasedLog) {
        final String sql = "INSERT INTO purchased(username, product_key, product_name, product_price, date) "
                + "VALUES(?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Product product : purchasedLog.products()) {
                pstmt.setString(1, purchasedLog.purchaser());
                pstmt.setInt(2, product.getKey());
                pstmt.setString(3, product.getProductName());
                pstmt.setInt(4, product.getPrice());
                pstmt.setString(5, purchasedLog.date());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
        } finally {
            DatabaseHandler.closeConnection();
        }
    }
}
