import com.yourcompany.banking.DBConnection;

import java.sql.*;

public class BankingOperations {

    // Method to create a new account
    public void createAccount(String name, String email, String password, String accountType, double initialDeposit) {
        try (Connection conn =  DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Insert user
            String userSql = "INSERT INTO Users(name, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, password);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long userId = generatedKeys.getLong(1);

                        // Insert account
                        String accountSql = "INSERT INTO Accounts(user_id, account_type, balance) VALUES (?, ?, ?)";
                        try (PreparedStatement accountStmt = conn.prepareStatement(accountSql)) {
                            accountStmt.setLong(1, userId);
                            accountStmt.setString(2, accountType);
                            accountStmt.setDouble(3, initialDeposit);
                            accountStmt.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }

            conn.commit(); // Commit transaction
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to deposit money into an account
    public void deposit(int accountId, double amount) {
        String depositSql = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(depositSql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Method to withdraw money from an account
    public void withdraw(int accountId, double amount) throws SQLException {
        String checkBalanceSql = "SELECT balance FROM Accounts WHERE account_id = ?";
        String withdrawSql = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ? AND balance >= ?";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceSql)) {
                checkStmt.setInt(1, accountId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    double currentBalance = rs.getDouble(1);
                    if (currentBalance >= amount) {
                        try (PreparedStatement withdrawStmt = conn.prepareStatement(withdrawSql)) {
                            withdrawStmt.setDouble(1, amount);
                            withdrawStmt.setInt(2, accountId);
                            withdrawStmt.setDouble(3, amount);
                            withdrawStmt.executeUpdate();
                        }
                    } else {
                        throw new SQLException("Insufficient funds");
                    }
                }
            }
        }
    }

    // Method to check the balance of an account
    public double getBalance(int accountId) {
        String balanceSql = "SELECT balance FROM Accounts WHERE account_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(balanceSql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0.0; // Return 0 if account is not found or an error occurs
    }
}
