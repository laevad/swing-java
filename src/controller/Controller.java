package controller;

import gui.FormEvent;
import model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {
    Database db = new Database();

    public List<Person> getPeople(){
        return  db.getPeople();
    }

    public void addPerson(FormEvent ev){
        String name =  ev.getName();
        String occupation = ev.getOccupation();
        int ageCatId = ev.getAgeCategory();
        String empCat = ev.getEmpCat();
        boolean isUs = ev.isUsCitizen();
        String taxId = ev.getTaxId();
        String gender = ev.getGender();

        AgeCategory ageCategory  = null;

        switch (ageCatId){
            case 0:
                ageCategory = AgeCategory.child;
                break;
            case 1:
                ageCategory = AgeCategory.adult;
                break;
            case 2:
                ageCategory = AgeCategory.senior;
                break;
        }

        EmploymentCategory employmentCategory = null;
        switch (empCat) {
            case "employed":
                employmentCategory = EmploymentCategory.employed;
                break;
            case "self-employed":
                employmentCategory = EmploymentCategory.selfEmployed;
                break;
            case "unemployed":
                employmentCategory = EmploymentCategory.unEmployed;
                break;
            default:
                System.err.println(empCat);
                break;
        }

        Gender genderCat;

        if(gender.equals("male")){
            genderCat = Gender.male;
        }else {
            genderCat = Gender.female;
        }

        Person person = new Person(
                name,
                occupation,
                ageCategory,
                employmentCategory,
                taxId,
                isUs,
                genderCat
        );
        db.addPerson(person);
    }
    public void saveToFile(File file) throws IOException {
        db.saveToFile(file);
    }
    public void loadFromFile(File file) throws IOException, ClassNotFoundException {
        db.loadFromFile(file);
    }
}
