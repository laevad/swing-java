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
        for (Person person: people){
            int id = person.getId();
            checkPreparedStatement.setInt(1, id);
            ResultSet checkResult = checkPreparedStatement.executeQuery();
            checkResult.next();
            int count = checkResult.getInt(1);
            System.out.println("Count for person with id "+ id + " is "+ count);
        }
        checkPreparedStatement.close();
    }
}
