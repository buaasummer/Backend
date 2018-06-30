package com.summer.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.summer.demo.Entity.Meeting;
import com.summer.demo.Repository.MeetingRepository;
import org.springframework.web.multipart.MultipartFile;
import com.summer.demo.StaticClass.DateParser;
import com.summer.demo.Entity.Institution;
import com.summer.demo.Repository.InstitutionRepository;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    //新建会议
    @PostMapping(value = "/meeting/create")
    public int createMeeting(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        //if(file==null)return 0;//附件上传错误
        if(request.getParameter("institution")==null)return 0;//无id
        Meeting meeting = new Meeting();
        meeting.setTitle(request.getParameter("title"));
        meeting.setIntroduction(request.getParameter("introduction"));
        meeting.setAddress(request.getParameter("address"));
        java.sql.Date startdate=new java.sql.Date(DateParser.stringToDate(request.getParameter("startdate")).getTime());
        meeting.setStartDate(startdate);
        java.sql.Date enddate=new java.sql.Date(DateParser.stringToDate(request.getParameter("enddate")).getTime());
        meeting.setEndDate(enddate);
        meeting.setSchedule(request.getParameter("schedule"));
        meeting.setPaperInfo(request.getParameter("paperinfo"));
        java.sql.Date poststartdate=new java.sql.Date(DateParser.stringToDate(request.getParameter("poststartdate")).getTime());
        meeting.setPostStartDate(poststartdate);
        java.sql.Date postenddate=new java.sql.Date(DateParser.stringToDate(request.getParameter("postenddate")).getTime());
        meeting.setPostEndDate(postenddate);
        java.sql.Date informdate=new java.sql.Date(DateParser.stringToDate(request.getParameter("informdate")).getTime());
        meeting.setInformDate(informdate);
        java.sql.Date registstartdate=new java.sql.Date(DateParser.stringToDate(request.getParameter("registstartdate")).getTime());
        meeting.setRegistStartDate(registstartdate);
        java.sql.Date registenddate=new java.sql.Date(DateParser.stringToDate(request.getParameter("registenddate")).getTime());
        meeting.setRegistrationDeadline(registenddate);
        meeting.setRegistrationFee(request.getParameter("registrationfee"));
        String institution_name=request.getParameter("institution_name");
        Institution institution=institutionRepo.findByInstitutionName(institution_name);
        meeting.setInstitution(institution);
        meeting.setContactPerson(request.getParameter("contactperson"));
        meeting.setEmail(request.getParameter("email"));
        meeting.setPhone(request.getParameter("phone"));
        meeting.setAccommodationAndTraffic(request.getParameter("traffic"));
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
}
