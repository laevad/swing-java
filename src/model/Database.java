package model;

import javax.xml.transform.Result;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {
    private List<Person> people;
    private Connection connection;
    public Database(){
        people = new LinkedList<>();
    }
    public void addPerson(Person person){
        people.add(person);
    }
    public List<Person> getPeople(){
        return Collections.unmodifiableList(people);
    }
    public void saveToFile(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        Person[] persons = people.toArray(new Person[people.size()]);
        objectOutputStream.writeObject(persons);
        objectOutputStream.close();
    }
    public void loadFromFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Person[] persons = (Person[]) objectInputStream.readObject();
        people.clear();
        people.addAll(Arrays.asList(persons));
        objectInputStream.close();
    }

    public void removePerson(int index) {
        people.remove(index);
    }
    public void connect() throws ClassNotFoundException, SQLException {
        if (connection != null) return;
        Class.forName("com.mysql.jdbc.Driver");

        String connectionUrl = "jdbc:mysql://localhost:3306/swingtest";
        connection = DriverManager.getConnection(connectionUrl, "root", "");
        System.out.println("Connected: "+ connection);
    }
    public void disconnect() throws SQLException {
        if (connection != null){
            connection.close();
        }
    }
    public void save() throws SQLException {
        String checkSql = "Select count(*) as count from people where id=?";
        PreparedStatement checkPreparedStatement = connection.prepareStatement(checkSql);

        String insertSql = "insert into people (id, name, age , employment_status, tax_id, " +
                "us_citizen, gender, occupation) values (? , ? , ? , ? , ? , ? , ? , ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);

        String updateSql = "update people set name=?, age=? , employment_status=?, tax_id=?, " +
                "us_citizen=?, gender=?, occupation=? where id=?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);

        for (Person person: people){
            int id = person.getId();
            String name = person.getName();
            String occupation = person.getOccupation();
            AgeCategory age = person.getAgeCategory();
            EmploymentCategory emp = person.getEmploymentCategory();
            String tax = person.getTaxId();
            boolean isUs = person.isUsCitizen();
            Gender gender = person.getGender();
            checkPreparedStatement.setInt(1, id);
            ResultSet checkResult = checkPreparedStatement.executeQuery();
            checkResult.next();
            int count = checkResult.getInt(1);

            if(count == 0){
                System.out.println("Insert id: " + id);
                int col = 1;
                insertStatement.setInt(col++, id);
                insertStatement.setString(col++, name);
                insertStatement.setString(col++, age.name());
                insertStatement.setString(col++, emp.name());
                insertStatement.setString(col++, tax);
                insertStatement.setBoolean(col++, isUs);
                insertStatement.setString(col++, gender.name());
                insertStatement.setString(col++, occupation);

                insertStatement.executeUpdate();
            }else {
                System.out.println("update id: "+ id);

                int col = 1;
                updateStatement.setString(col++, name);
                updateStatement.setString(col++, age.name());
                updateStatement.setString(col++, emp.name());
                updateStatement.setString(col++, tax);
                updateStatement.setBoolean(col++, isUs);
                updateStatement.setString(col++, gender.name());
                updateStatement.setString(col++, occupation);
                updateStatement.setInt(col++, id);

                updateStatement.executeUpdate();
            }

            System.out.println("Count for person with id "+ id + " is "+ count);
        }
        updateStatement.close();
        insertStatement.close();
        checkPreparedStatement.close();
    }
    public void load() throws SQLException {
        people.clear();

        String sql = "select * from people order by name ";
        Statement selectStatement = connection.createStatement();

        ResultSet results = selectStatement.executeQuery(sql);
        while (results.next()){
            int id = results.getInt("id");
            String name = results.getString("name");
            String age = results.getString("age");
            String emp = results.getString("employment_status");
            String tax = results.getString("tax_id");
            boolean isUs = results.getBoolean("us_citizen");
            String gender = results.getString("gender");
            String occupation = results.getString("occupation");

            Person person = new Person(id, name, occupation, AgeCategory.valueOf(age), EmploymentCategory.valueOf(emp), tax,
                    isUs, Gender.valueOf(gender));
            people.add(person);

            System.out.println(person);
        }
        results.close();
        selectStatement.close();
    }
}
