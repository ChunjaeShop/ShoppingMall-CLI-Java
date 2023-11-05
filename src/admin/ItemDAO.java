package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemDAO {
    private static ItemDAO itemDAO;
    private Connection conn;

    public ItemDAO() {

    }

    public static ItemDAO getInstance() {
        if (itemDAO == null) {
            itemDAO = new ItemDAO();
        }
        return itemDAO;
    }

    public void setConnection(Connection con) {
        this.conn = con;
    }
    
}