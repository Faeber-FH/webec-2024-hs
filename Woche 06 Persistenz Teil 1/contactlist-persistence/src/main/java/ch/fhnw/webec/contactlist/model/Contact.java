package ch.fhnw.webec.contactlist.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Contact {

    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    @ElementCollection
    private List<String> email;
    @ElementCollection
    private List<String> phone;
    private String jobTitle;
    private String company;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean containsString(String query) {
        var q = query.toLowerCase();
        return firstName.toLowerCase().contains(q) ||
            lastName.toLowerCase().contains(q) ||
            email.stream().anyMatch(x -> x.toLowerCase().contains(q)) ||
            phone.stream().anyMatch(x -> x.toLowerCase().contains(q));
    }
}
