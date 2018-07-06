package com.summer.demo.Entity;


import javax.persistence.*;

@Entity
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int participantsId;
    private String participantIdList;
    private int paperNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private PersonalUser personalUser;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    //缴费证明文件
    private String downloadUrl;

    public int getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(int participantsId) {
        this.participantsId = participantsId;
    }

    public String getParticipantIdList() {
        return participantIdList;
    }

    public void setParticipantIdList(String participantIdList) {
        this.participantIdList = participantIdList;
    }

    public int getPaperNumber() {
        return paperNumber;
    }

    public void setPaperNumber(int paperNumber) {
        this.paperNumber = paperNumber;
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
