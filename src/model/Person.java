package model;

import java.io.Serializable;

public class Person implements Serializable {
    private  static int count = 1;
    private int id;
    private String name;
    private String occupation;
    private AgeCategory ageCategory;
    private Gender gender;
    private EmploymentCategory employmentCategory;
    private String taxId;
    private boolean usCitizen;

    public Person(
            String name,
            String occupation, AgeCategory ageCategory,
            EmploymentCategory employmentCategory, String taxId,
            boolean usCitizen, Gender gender
    ) {
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.employmentCategory = employmentCategory;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.gender = gender;
        this.id = count;
        count++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EmploymentCategory getEmploymentCategory() {
        return employmentCategory;
    }

    public void setEmploymentCategory(EmploymentCategory employmentCategory) {
        this.employmentCategory = employmentCategory;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public boolean isUsCitizen() {
        return usCitizen;
    }

    public void setUsCitizen(boolean usCitizen) {
        this.usCitizen = usCitizen;
    }


}
