package com.medical.healthcare.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "drug_id")
    private Drug drug=new Drug();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user=new User();

    public Prescription() {
    }

    public Prescription(Long id, Date date, Drug drug, User user) {
        this.id = id;
        this.date = date;
        this.drug = drug;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

}
