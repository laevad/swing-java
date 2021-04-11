import model.*;

import java.sql.SQLException;

public class TestDatabase {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println("running db");

        Database db = new Database();
        db.connect();
        db.addPerson(new Person("david kun", "pro", AgeCategory.adult, EmploymentCategory.employed, "143", true, Gender.male));
        db.addPerson(new Person("Dave", "pro", AgeCategory.adult, EmploymentCategory.employed, "123", true, Gender.male));
        db.save();
        db.load();
        db.disconnect();
    }
}
