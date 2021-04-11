import model.Database;

import java.sql.SQLException;

public class TestDatabase {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println("running db");

        Database db = new Database();
        db.connect();
        db.disconnect();
    }
}
