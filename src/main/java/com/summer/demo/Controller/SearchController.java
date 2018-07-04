package com.summer.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.summer.demo.Repository.MeetingRepository;
import org.springframework.data.domain.Page;
import com.summer.demo.Entity.Meeting;
import com.summer.demo.StaticClass.DateParser;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

@CrossOrigin
@RestController
public class SearchController {
    @Autowired
    private MeetingRepository meetingRepo;

    @GetMapping(value = "/search/meeting")
    public List<Meeting> getByMeetingTitle(@RequestParam(value = "keyword", defaultValue = "*") String keyword,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "20") int size,
                                           @RequestParam(value = "startdate", defaultValue = "1000/1/1") String startDate,
                                           @RequestParam(value = "enddate", defaultValue = "9000/1/1") String endDate,
                                           @RequestParam(value = "isonposting", defaultValue = "0") int isOnPosting,
                                           @RequestParam(value = "isonregistration", defaultValue = "0") int isOnRegistration){
        List<Meeting> meetingList=new ArrayList<>();
        Pageable pageable=new PageRequest(page,size, Sort.Direction.ASC,"meetingId");
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date startdate = new java.sql.Date(DateParser.stringToDate(startDate).getTime());
        java.sql.Date enddate = new java.sql.Date(DateParser.stringToDate(endDate).getTime());
        java.util.Date currentDate=new java.util.Date();
        java.sql.Date curdate=new java.sql.Date(DateParser.stringToDate(format1.format(currentDate)).getTime());
        if(isOnPosting==1){
            meetingList=meetingRepo.findAllByGivenPost(keyword,startdate,enddate,curdate,pageable).getContent();
        }else if(isOnRegistration==1){
            meetingList=meetingRepo.findAllByGivenRegist(keyword,startdate,enddate,curdate,pageable).getContent();
        }else{
            meetingList=meetingRepo.findAllByGivenNone(keyword,startdate,enddate,pageable).getContent();
        }

        return meetingList;
    }
}
