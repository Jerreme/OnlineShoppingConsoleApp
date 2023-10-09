package database;

import views.Warn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("CallToPrintStackTrace")
public class DatabaseHandler {
    private static Connection conn;
    private static final String url = "jdbc:sqlite:OnlineShopping.db";

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            Warn.debugMessage(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTable(String sql) {
        DatabaseHandler.getConnection();
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            Warn.debugMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseHandler.closeConnection();
        }
    }

    public static void createAdminTable() {
        String sql = "CREATE TABLE IF NOT EXISTS admins (\n"
                + "	username TEXT PRIMARY KEY NOT NULL,\n"
                + "	password TEXT NOT NULL\n"
                + ")";
        createTable(sql);
    }

    public static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	username TEXT PRIMARY KEY NOT NULL,\n"
                + "	password TEXT NOT NULL,\n"
                + "	balance int NOT NULL\n"
                + ")";
        createTable(sql);
    }

    public static void createCartTable() {
        String sql = "CREATE TABLE IF NOT EXISTS cart (\n"
                + "	username TEXT NOT NULL,\n"
                + "	product_key int NOT NULL,\n"
                + "	product_name TEXT NOT NULL,\n"
                + "	product_price int NOT NULL\n"
                + ")";
        createTable(sql);
    }

    public static void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (\n"
                + "	product_key int PRIMARY KEY NOT NULL,\n"
                + "	product_name TEXT NOT NULL,\n"
                + "	product_price int NOT NULL\n"
                + ")";
        createTable(sql);
    }

    public static void createPurchasedTable() {
        String sql = "CREATE TABLE IF NOT EXISTS purchased (\n"
                + "	username TEXT NOT NULL,\n"
                + "	product_key int NOT NULL,\n"
                + "	product_name TEXT NOT NULL,\n"
                + "	product_price int NOT NULL,\n"
                + "	date TEXT NOT NULL\n"
                + ")";
        createTable(sql);
    }
}

