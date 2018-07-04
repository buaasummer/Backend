package com.summer.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.summer.demo.Entity.Meeting;
import com.summer.demo.Repository.MeetingRepository;
import org.springframework.web.multipart.MultipartFile;
import com.summer.demo.StaticClass.DateParser;
import com.summer.demo.Entity.Institution;
import com.summer.demo.Repository.InstitutionRepository;

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

    //新建会议
    @PostMapping(value = "/meeting/create")
    public int createMeeting(@RequestParam(value="file", defaultValue = "") MultipartFile file,HttpServletRequest request){
        //if(file==null)return 0;//附件上传错误
        if(request.getParameter("institution_name")==null)return 0;//无id
        Meeting meeting = new Meeting();
        meeting.setTitle(request.getParameter("title"));
        meeting.setIntroduction(request.getParameter("introduction"));
        meeting.setAddress(request.getParameter("address"));
        if(request.getParameter("startdate")!=null&&request.getParameter("startdate")!=""){
            java.sql.Date startdate=new java.sql.Date(DateParser.stringToDate(request.getParameter("startdate")).getTime());
            meeting.setStartDate(startdate);
        }
        if(request.getParameter("enddate")!=null&&request.getParameter("enddate")!=""){
            java.sql.Date enddate=new java.sql.Date(DateParser.stringToDate(request.getParameter("enddate")).getTime());
            meeting.setEndDate(enddate);
        }
        meeting.setSchedule(request.getParameter("schedule"));
        meeting.setPaperInfo(request.getParameter("paperinfo"));

        if(request.getParameter("poststartdate")!=null&&request.getParameter("poststartdate")!=""){
            java.sql.Date poststartdate=new java.sql.Date(DateParser.stringToDate(request.getParameter("poststartdate")).getTime());
            meeting.setPostStartDate(poststartdate);
        }

        if(request.getParameter("postenddate")!=null&&request.getParameter("postenddate")!=""){
            java.sql.Date postenddate=new java.sql.Date(DateParser.stringToDate(request.getParameter("postenddate")).getTime());
            meeting.setPostEndDate(postenddate);
        }

        if(request.getParameter("informdate")!=null&&request.getParameter("informdate")!=""){
            java.sql.Date informdate=new java.sql.Date(DateParser.stringToDate(request.getParameter("informdate")).getTime());
            meeting.setInformDate(informdate);
        }

        if(request.getParameter("registstartdate")!=null&&request.getParameter("registstartdate")!=""){
            java.sql.Date registstartdate=new java.sql.Date(DateParser.stringToDate(request.getParameter("registstartdate")).getTime());
            meeting.setRegistStartDate(registstartdate);
        }

        if(request.getParameter("registenddate")!=null&&request.getParameter("registenddate")!=""){
            java.sql.Date registenddate=new java.sql.Date(DateParser.stringToDate(request.getParameter("registenddate")).getTime());
            meeting.setRegistrationDeadline(registenddate);
        }

        meeting.setRegistrationFee(request.getParameter("registrationfee"));
        String institution_name=request.getParameter("institution_name");
        Institution institution=institutionRepo.findByInstitutionName(institution_name);
        meeting.setInstitution(institution);
        meeting.setContactPerson(request.getParameter("contactperson"));
        meeting.setEmail(request.getParameter("email"));
        meeting.setPhone(request.getParameter("phone"));
        meeting.setAccommodationAndTraffic(request.getParameter("traffic"));

        String fileName = file.getOriginalFilename();
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
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
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
}
