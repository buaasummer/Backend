package com.summer.demo.AssitClass;

import com.summer.demo.Entity.Author;
import com.summer.demo.Entity.PersonalUser;

import java.util.List;

public class BigPaper {
    private int paperId;

    private String title;

    private String  names;
    private String organizations;
    private String  paperAbstract;
    //1代表录用，2代表修改后录用，3代表待审核，4代表未录用。
    private int status;

    //论文下载url
    private String downloadUrl;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getOrganizations() {
        return organizations;
    }

    public void setOrganizations(String organizations) {
        this.organizations = organizations;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
