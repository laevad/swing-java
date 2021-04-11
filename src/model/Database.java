package model;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

        String connectionUrl = "jdbc:mysql://localhost:3306/";
        connection = DriverManager.getConnection(connectionUrl, "root", "");
        System.out.println("Connected: "+ connection);
    }
    public void disconnect() throws SQLException {
        if (connection != null){
            connection.close();
        }
    }
}
