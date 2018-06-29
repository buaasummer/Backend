package com.summer.demo.Entity;

import javax.persistence.*;

@Entity
public class InstitutionUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String password;

    private String username;

   @ManyToOne
   @JoinColumn(name = "institution_id",foreignKey = @ForeignKey(name = "Institution_Institution_ID_FK"))
   private Institution institution;

    public void setUserId(int userId) {
        this.userId = userId;
    }

   public Institution getInstitution() {
       return institution;
   }

   public void setInstitution(Institution institution) {
       this.institution = institution;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
