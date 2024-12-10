package com.medical.healthcare.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table
public class Pharmacy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String Address;
    private String phone;

    public Pharmacy() {
    }

    public Pharmacy(Long id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        Address = address;
        this.phone = phone;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
