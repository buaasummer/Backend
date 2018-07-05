package com.summer.demo.AssitClass;
import org.springframework.web.multipart.MultipartFile;

public class AssitMeeting {
    private MultipartFile file;
    private String institution_name;
    private String title;
    private String introduction;
    private String address;
    private String startdate;
    private String enddate;
    private String schedule;
    private String poststartdate;
    private String postenddate;
    private String informdate;
    private String paperinfo;
    private String registstartdate;
    private String registenddate;
    private String registrationfee;
    private String contactperson;
    private String email;
    private String phone;
    private String traffic;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
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

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getPoststartdate() {
        return poststartdate;
    }

    public void setPoststartdate(String poststartdate) {
        this.poststartdate = poststartdate;
    }

    public String getPostenddate() {
        return postenddate;
    }

    public void setPostenddate(String postenddate) {
        this.postenddate = postenddate;
    }

    public String getInformdate() {
        return informdate;
    }

    public void setInformdate(String informdate) {
        this.informdate = informdate;
    }

    public String getRegiststartdate() {
        return registstartdate;
    }

    public void setRegiststartdate(String registstartdate) {
        this.registstartdate = registstartdate;
    }

    public String getRegistenddate() {
        return registenddate;
    }

    public void setRegistenddate(String registenddate) {
        this.registenddate = registenddate;
    }

    public String getRegistrationfee() {
        return registrationfee;
    }

    public void setRegistrationfee(String registrationfee) {
        this.registrationfee = registrationfee;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaperinfo() {
        return paperinfo;
    }

    public void setPaperinfo(String paperinfo) {
        this.paperinfo = paperinfo;
    }
}
