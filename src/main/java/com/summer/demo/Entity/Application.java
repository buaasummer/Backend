package com.summer.demo.Entity;
import javax.persistence.*;
import com.summer.demo.Entity.Institution;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int applicationId;

    private String time;

    @OneToOne
    @JoinColumn(name = "institution_id", foreignKey = @ForeignKey(name = "Application_Institution_ID_FK2"))
    private Institution institution;

    @OneToOne
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "Application_InstitutionUser_ID_FK2"))
    private InstitutionUser institutionuser;

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public InstitutionUser getInstitutionuser() {
        return institutionuser;
    }

    public void setInstitutionuser(InstitutionUser institutionuser) {
        this.institutionuser = institutionuser;
    }
}
