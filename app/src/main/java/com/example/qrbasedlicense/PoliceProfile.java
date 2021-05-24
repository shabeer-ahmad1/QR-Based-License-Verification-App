package com.example.qrbasedlicense;

import java.io.Serializable;

public class PoliceProfile implements Serializable {
    private String cnic,name,email,designation,contactno;
    public PoliceProfile(){}

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactno() {
        return contactno;
    }

    public String getDesignation() {
        return designation;
    }

    public String getCnic() {
        return cnic;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
