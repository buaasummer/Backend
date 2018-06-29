package com.summer.demo.Entity;

import javax.persistence.*;


@Entity
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paperId;

    private String title;

    private String  authorIds;


    private String  paperAbstract;

    @ManyToOne
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "PERSONALUSER_USER_ID_FK"))
    private PersonalUser personalUser;

    @ManyToOne
    @JoinColumn(name = "meeting_id",foreignKey = @ForeignKey(name ="MEETING_MEETING_ID_FK"))
    private Meeting meeting;

    //论文下载url
    private String downloadUrl;

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public String getAuthorIds() {

        return authorIds;
    }

    public void setAuthorIds(String authorIds) {
        this.authorIds = authorIds;
    }

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
