package com.summer.demo.Entity;

import javax.persistence.*;

@Entity
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paperId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "userId")
    private PersonalUser personalUser;

    @ManyToOne
    @JoinColumn(name = "meetingId")
    private Meeting meeting;

    //论文下载url
    private String downloadUrl;

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PersonalUser getPersonalUser() {
        return personalUser;
    }

    public void setPersonalUser(PersonalUser personalUser) {
        this.personalUser = personalUser;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
