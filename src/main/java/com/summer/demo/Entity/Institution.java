package com.summer.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int institutionId;

    //组织机构代码
    private String organizationCode;

    //组织名称
    private String institutionName;

    //法人名称
    private String legalPersonName;

    //法人邮箱
    private String legalPersonEmail;

    //法人电话
    private String legalPersonPhoneNumber;

    //纳税人编号
    private String taxPayerCode;

    //通讯地址
    private String postalAddress;

    //审核资料
    private String downloadUrl;

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getLegalPersonEmail() {
        return legalPersonEmail;
    }

    public void setLegalPersonEmail(String legalPersonEmail) {
        this.legalPersonEmail = legalPersonEmail;
    }

    public String getLegalPersonPhoneNumber() {
        return legalPersonPhoneNumber;
    }

    public void setLegalPersonPhoneNumber(String legalPersonPhoneNumber) {
        this.legalPersonPhoneNumber = legalPersonPhoneNumber;
    }

    public String getTaxPayerCode() {
        return taxPayerCode;
    }

    public void setTaxPayerCode(String taxPayerCode) {
        this.taxPayerCode = taxPayerCode;
    }

    public int getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }
}
