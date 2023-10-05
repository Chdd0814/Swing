package ChocolateInventoryApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class chocoDAO {
    private static Connection con;

    public chocoDAO(Connection con) {
        this.con = con;
    }
    
    public List<chocoDTO> getChocolates() {
        List<chocoDTO> chocolates = new ArrayList<>();

        try {
            String query = "SELECT * FROM chocolate";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int chocoID = rs.getInt("chocoID");
                String chocoName = rs.getString("chocoName");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");

                chocoDTO choco = new chocoDTO(chocoID,chocoName,price,quantity);

                chocolates.add(choco);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chocolates;
    }
    
    public boolean addChocolate(chocoDTO chocolate) {
        try {
            String query = "INSERT INTO chocolate (chocoName, price, quantity) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, chocolate.getChocoName());
            pstmt.setInt(2, chocolate.getPrice());
            pstmt.setInt(3, chocolate.getQuantity());
            int result = pstmt.executeUpdate();

            return result > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteChocolate(String chocoName) {
        try {
            String query = "DELETE FROM chocolate WHERE chocoName=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, chocoName);
            int result = pstmt.executeUpdate();

            return result > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean addQuantity(String chocoName, int quantityAdd) {
        try {
            String query = "UPDATE chocolate SET quantity = quantity + ? WHERE chocoName = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, quantityAdd);
            pstmt.setString(2, chocoName);
            int result = pstmt.executeUpdate();

            return result > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean chocoNameCheck(String chocoName) {
        try {
            String query = "SELECT * FROM chocolate WHERE chocoName = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, chocoName);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean orderChocolate(String chocoName, int quantity) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE chocolate SET quantity = quantity - ? WHERE chocoName = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, quantity);
            stmt.setString(2, chocoName);

            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean changeChocolatePrice(String chocoName, int newPrice) {
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE chocolate SET price = ? WHERE chocoName = ?");
            stmt.setInt(1, newPrice);
            stmt.setString(2, chocoName);

            int rowsAffected = stmt.executeUpdate();

            stmt.close();
            con.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
