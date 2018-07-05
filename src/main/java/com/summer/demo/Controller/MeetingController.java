package com.summer.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.summer.demo.Entity.Meeting;
import com.summer.demo.Repository.MeetingRepository;
import org.springframework.web.multipart.MultipartFile;
import com.summer.demo.StaticClass.DateParser;
import com.summer.demo.Entity.Institution;
import com.summer.demo.Repository.InstitutionRepository;
import com.summer.demo.Entity.PersonalUser;
import com.summer.demo.Repository.PersonalUserRepository;

import com.summer.demo.AssitClass.AssitMeeting;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class MeetingController {
    private String filePath="C:\\Users\\Administrator\\Documents\\release1\\upload\\paper_model\\";
    @Autowired
    private MeetingRepository meetingRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @Autowired
    private PersonalUserRepository personalUserRepo;

    //新建会议
    @PostMapping(value = "/meeting/create")
    public int createMeeting(AssitMeeting assitMeeting){
        //if(file==null)return 0;//附件上传错误
        if(assitMeeting.getInstitution_name().equals("")||assitMeeting.getInstitution_name().isEmpty())return 0;//无id
        Meeting meeting = new Meeting();
        meeting.setTitle(assitMeeting.getTitle());
        meeting.setIntroduction(assitMeeting.getIntroduction());
        meeting.setAddress(assitMeeting.getAddress());
        if(!assitMeeting.getStartdate().equals("")&&!assitMeeting.getStartdate().isEmpty()){
            java.sql.Date startdate=new java.sql.Date(DateParser.stringToDate(assitMeeting.getStartdate()).getTime());
            meeting.setStartDate(startdate);
        }
        if(!assitMeeting.getEnddate().equals("")&&!assitMeeting.getEnddate().isEmpty()){
            java.sql.Date enddate=new java.sql.Date(DateParser.stringToDate(assitMeeting.getEnddate()).getTime());
            meeting.setEndDate(enddate);
        }
        meeting.setSchedule(assitMeeting.getSchedule());
        meeting.setPaperInfo(assitMeeting.getPaperinfo());

        if(!assitMeeting.getPoststartdate().equals("")&&!assitMeeting.getPoststartdate().isEmpty()){
            java.sql.Date poststartdate=new java.sql.Date(DateParser.stringToDate(assitMeeting.getPoststartdate()).getTime());
            meeting.setPostStartDate(poststartdate);
        }

        if(!assitMeeting.getPostenddate().equals("")&&!assitMeeting.getPostenddate().isEmpty()){
            java.sql.Date postenddate=new java.sql.Date(DateParser.stringToDate(assitMeeting.getPostenddate()).getTime());
            meeting.setPostEndDate(postenddate);
        }

        if(!assitMeeting.getInformdate().equals("")&&!assitMeeting.getInformdate().isEmpty()){
            java.sql.Date informdate=new java.sql.Date(DateParser.stringToDate(assitMeeting.getInformdate()).getTime());
            meeting.setInformDate(informdate);
        }

        if(!assitMeeting.getRegiststartdate().equals("")&&!assitMeeting.getRegiststartdate().isEmpty()){
            java.sql.Date registstartdate=new java.sql.Date(DateParser.stringToDate(assitMeeting.getRegiststartdate()).getTime());
            meeting.setRegistStartDate(registstartdate);
        }

        if(!assitMeeting.getRegistenddate().equals("")&&!assitMeeting.getRegistenddate().isEmpty()){
            java.sql.Date registenddate=new java.sql.Date(DateParser.stringToDate(assitMeeting.getRegistenddate()).getTime());
            meeting.setRegistrationDeadline(registenddate);
        }

        meeting.setRegistrationFee(assitMeeting.getRegistrationfee());
        String institution_name=assitMeeting.getInstitution_name();
        Institution institution=institutionRepo.findByInstitutionName(institution_name);
        meeting.setInstitution(institution);
        meeting.setContactPerson(assitMeeting.getContactperson());
        meeting.setEmail(assitMeeting.getEmail());
        meeting.setPhone(assitMeeting.getPhone());
        meeting.setAccommodationAndTraffic(assitMeeting.getTraffic());

        if (assitMeeting.getFile()==null||assitMeeting.getFile().isEmpty()){
            String fileName = assitMeeting.getFile().getOriginalFilename();
            // 文件后缀
            //String suffixName = fileName.substring(fileName.lastIndexOf("."));
            // 重新生成唯一文件名，用于存储数据库
            //String newFileName = UUID.randomUUID().toString()+suffixName;

            String url=filePath + fileName;
            //创建文件
            File dest = new File(url);
            url="154.8.211.55:8081/paper_model/"+fileName;
            //newinstitution.setDownloadUrl(url);
            meeting.setModelDownloadUrl(url);
            try {
                assitMeeting.getFile().transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        meetingRepo.save(meeting);
        return meeting.getMeetingId();
    }

    //获得某个会议信息
    @GetMapping(value = "/meeting/info")
    public Meeting getMeetingInfo(@RequestParam("meeting_id") int meeting_id){
        if(meeting_id==0){
            return null;
        }else{
            return meetingRepo.findByMeetingId(meeting_id);
        }
    }

    //判断会议是否属于该单位用户
    @GetMapping(value = "/meeting/isMatch")
    public Boolean isMatch(@RequestParam("meetingId") int meetingId,@RequestParam("institutionId") int institutionId)
    {
        Meeting meeting=meetingRepo.findByMeetingId(meetingId);
        if(institutionId==meeting.getInstitution().getInstitutionId())
        {
            return true;
        }
        else  return false;
    }

    @GetMapping(value = "/meeting/personaluser_register")
    public boolean normalUserRegister(@RequestParam(value = "user_id") int userId, @RequestParam(value = "meeting_id") int meetingId){
        PersonalUser user=personalUserRepo.findByUserId(userId);
        if(user!=null){
            Meeting meeting=meetingRepo.findByMeetingId(meetingId);
            if(meeting!=null){
                //发送确认邮件
                return true;
            }
        }
        return false;
    }
}
