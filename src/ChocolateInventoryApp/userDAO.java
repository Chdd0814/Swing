package ChocolateInventoryApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDAO {
    private Connection con;

    public userDAO(Connection con) {
        this.con = con;
    }

    public boolean registerUser(userDTO user) {
        try {
            String query = "INSERT INTO user (id, password, name, tel) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getPass());
            pstmt.setString(3, user.getName());
            pstmt.setInt(4, user.getTel());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean loginUser(String id, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM user WHERE id=? AND password=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            boolean result = rs.next();

            rs.close();
            pstmt.close();
            con.close();

            return result;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
