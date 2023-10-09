package models;

import java.util.ArrayList;
import java.util.Date;

public record PurchasedLog(String purchaser, ArrayList<Product> products, String date) {
}
