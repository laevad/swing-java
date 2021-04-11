package model;

import java.io.*;
import java.util.*;

public class Database {
    private List<Person> people;
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
}
