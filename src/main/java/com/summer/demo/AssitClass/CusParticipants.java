package com.summer.demo.AssitClass;

public class CusParticipants {
    private int paperNumber;
    //缴费证明文件
    private String downloadUrl;
    private String paperTitle;
    private String names;
    private String genders;
    private String emails;
    private String bookAccommodations;

    public int getPaperNumber() {
        return paperNumber;
    }

    public void setPaperNumber(int paperNumber) {
        this.paperNumber = paperNumber;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getGenders() {
        return genders;
    }

    public void setGenders(String genders) {
        this.genders = genders;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getBookAccommodations() {
        return bookAccommodations;
    }

    public void setBookAccommodations(String bookAccommodations) {
        this.bookAccommodations = bookAccommodations;
    }
}
