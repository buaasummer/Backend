package com.summer.demo.Entity;


import javax.persistence.*;

@Entity
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meetingId;

    private String title;

    private String introduction;

    //征文信息
    private String solicitingInformation;

    //截稿日期
    private String submissionDeadline;

    //注册截止日期
    private String registrationDeadline;

    //会议日期
    private String conferenceDate;

    //日程安排(待拆分的字段）
    private String schedule;

    @ManyToOne
    @JoinColumn(name="institution")
    //组织机构
    private InstitutionUser institutionUser ;

    //论文模板,该字段存的是论文模板的下载url
    private String modelDownloadUrl;

    //注册费用
    private String registrationFee;

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSolicitingInformation() {
        return solicitingInformation;
    }

    public void setSolicitingInformation(String solicitingInformation) {
        this.solicitingInformation = solicitingInformation;
    }

    public String getSubmissionDeadline() {
        return submissionDeadline;
    }

    public void setSubmissionDeadline(String submissionDeadline) {
        this.submissionDeadline = submissionDeadline;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(String registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getConferenceDate() {
        return conferenceDate;
    }

    public void setConferenceDate(String conferenceDate) {
        this.conferenceDate = conferenceDate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getModelDownloadUrl() {
        return modelDownloadUrl;
    }

    public void setModelDownloadUrl(String modelDownloadUrl) {
        this.modelDownloadUrl = modelDownloadUrl;
    }

    public String getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
    }

    public String getAccommodationAndTraffic() {
        return accommodationAndTraffic;
    }

    public void setAccommodationAndTraffic(String accommodationAndTraffic) {
        this.accommodationAndTraffic = accommodationAndTraffic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //住宿交通
    private String accommodationAndTraffic;

    //邮箱（联系我们）
    private String email;

}
