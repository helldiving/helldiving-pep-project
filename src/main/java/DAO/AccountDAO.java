package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

// create account
    // check if username exists
    // add new account if username does not exist
    // return generated id

// user login
    // only success if username and password match real account in database
    // response body shoud contain a JSON of the account, including its account_id
    // password at least 4 characters
    // response status should be 200 OK (which is default)
    // if login not successful, response status should be 401 (Unauthorized)
    // 


// look up user by id

public class AccountDAO {
    
    public Account createAccount(Account account) {

        Connection connection = ConnectionUtil.getConnection();

        // check if uername exists & add account if not
        // return generated id
        try {

            String sql1 = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatementSelect = connection.prepareStatement(sql1);

            preparedStatementSelect.setString(1, account.getUsername());

            ResultSet rs = preparedStatementSelect.executeQuery();
            if (!rs.next()) {
                
                String sql2 = "INSERT INTO account (username, password) VALUES (?, ?)";

                PreparedStatement preparedStatementInsert = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
                preparedStatementInsert.setString(1, account.getUsername());
                preparedStatementInsert.setString(2, account.getPassword());

                preparedStatementInsert.executeUpdate();
                ResultSet rs2 = preparedStatementInsert.getGeneratedKeys();
                if (rs2.next()) {
                    int generated_account_id = (int) rs2.getLong(1);
                    return new Account(generated_account_id, account.getUsername(), account.getPassword());
                }

            // if username exists, error
            } else {
                return null;

            }
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        return null;
    }

    public Account userLogin(Account account) {

        Connection connection = ConnectionUtil.getConnection();

        try {

        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return new Account(
                rs.getInt("account_id"), 
                rs.getString("username"), 
                rs.getString("password"));
        }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
