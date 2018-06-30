package com.summer.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Author {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int authorId;

    private String authorName;

    private int identificationNumber;

    private String organization;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
