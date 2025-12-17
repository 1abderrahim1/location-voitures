package AgenceLocation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCleaner {

    public static void clean(String[] args) {

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Enable FK constraints (important for SQLite)
            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.executeUpdate("DELETE FROM historique");
            stmt.executeUpdate("DELETE FROM reservations");
            stmt.executeUpdate("DELETE FROM clients");
            stmt.executeUpdate("DELETE FROM voitures");

            // Reset AUTOINCREMENT counters (SQLite specific)
            stmt.executeUpdate("DELETE FROM sqlite_sequence");

            System.out.println("✅ Database cleaned successfully.");

        } catch (SQLException e) {
            System.err.println("❌ Error cleaning database");
            e.printStackTrace();
        }
    }
}
