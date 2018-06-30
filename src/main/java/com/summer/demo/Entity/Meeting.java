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

    //征文信息
    private String paperInfo;

    //投稿日期
    private Date postStartDate;

    //截稿日期
    private Date postEndDate;

    //录用通知日期
    private Date informDate;

    //注册截止日期
    private Date registrationDeadline;

    //注册开始日期
    private Date registStartDate;

    //会议开始日期
    private Date startDate;

    //会议结束日期endDate
    private Date endDate;

    //日程安排(待拆分的字段）
    private String schedule;

    private String address;


    //论文模板,该字段存的是论文模板的下载url
    private String modelDownloadUrl;

    //注册费用
    private String registrationFee;

    //住宿交通
    private String accommodationAndTraffic;

    //邮箱（联系我们）
    private String email;

    //负责人名称
    private String contactPerson;

    //负责人电话
    private String phone;

    @ManyToOne
    @JoinColumn(name = "institution_id",foreignKey = @ForeignKey(name = "Institution_Institution_ID_FK2"))
    private Institution institution;

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccommodationAndTraffic() {
        return accommodationAndTraffic;
    }

    public void setAccommodationAndTraffic(String accommodationAndTraffic) {
        this.accommodationAndTraffic = accommodationAndTraffic;
    }

    public String getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(String registrationFee) {
        this.registrationFee = registrationFee;
    }

    public String getModelDownloadUrl() {
        return modelDownloadUrl;
    }

    public void setModelDownloadUrl(String modelDownloadUrl) {
        this.modelDownloadUrl = modelDownloadUrl;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getRegistStartDate() {
        return registStartDate;
    }

    public void setRegistStartDate(Date registStartDate) {
        this.registStartDate = registStartDate;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Date registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public Date getInformDate() {
        return informDate;
    }

    public void setInformDate(Date informDate) {
        this.informDate = informDate;
    }

    public Date getPostEndDate() {
        return postEndDate;
    }

    public void setPostEndDate(Date postEndDate) {
        this.postEndDate = postEndDate;
    }

    public Date getPostStartDate() {
        return postStartDate;
    }

    public void setPostStartDate(Date postStartDate) {
        this.postStartDate = postStartDate;
    }

    public String getPaperInfo() {
        return paperInfo;
    }

    public void setPaperInfo(String paperInfo) {
        this.paperInfo = paperInfo;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
