package com.summer.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.summer.demo.Entity.Meeting;
import com.summer.demo.Repository.MeetingRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class MeetingController {
    @Autowired
    private MeetingRepository meetingRepo;

    //新建会议
    @PostMapping(value = "/meeting/create")
    public int createMeeting(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if(file==null)return 0;//附件上传错误
        if(request.getParameter("institution")==null)return 0;//无id
        Meeting meeting = new Meeting();



        return 1;
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
