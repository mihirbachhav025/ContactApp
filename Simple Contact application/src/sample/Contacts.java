package sample;

import javafx.beans.property.SimpleStringProperty;

public class Contacts {
    private  String Name;
    private  String no;
    private  String email;

    public Contacts(String name, String no, String email) {
        Name = name;
        this.no = no;
        this.email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getName();
    }
}
