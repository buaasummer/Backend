package com.summer.demo.Entity;


import javax.persistence.*;
import java.sql.Date;


@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meetingId;

    private String title;

    private String introduction;

    //征稿主题

    //关于我们

    //征文信息
    private String solicitingInformation;

    //投稿日期
    private Date postStartDate;

    //截稿日期
    private String submissionDeadline;

    //录用通知日期
    private String informDate;

    //注册截止日期
    private String registrationDeadline;

    //会议开始日期
    private String startDate;

    //会议结束日期endDate

    //日程安排(待拆分的字段）
    private String schedule;


    //论文模板,该字段存的是论文模板的下载url
    private String modelDownloadUrl;

    //注册费用
    private String registrationFee;

    //住宿交通
    private String accommodationAndTraffic;

    //邮箱（联系我们）
    private String email;

    //负责人名称

    //负责人电话

    @ManyToOne
    @JoinColumn(name = "institution_id",foreignKey = @ForeignKey(name = "Institution_Institution_ID_FK2"))
    private Institution institution;

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
        return startDate;
    }

    public void setConferenceDate(String conferenceDate) {
        this.startDate = conferenceDate;
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

    public Institution getInstitution() {
        return institution;
    }

   public void setInstitution(Institution institution) {
       this.institution = institution;
    }

    public String getInformDate() {
        return informDate;
    }

    public void setInformDate(String informDate) {
        this.informDate = informDate;
    }

    public Date getPostStartDate() {
        return postStartDate;
    }

    public void setPostStartDate(Date postStartDate) {
        this.postStartDate = postStartDate;
    }
}
