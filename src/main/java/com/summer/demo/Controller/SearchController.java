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
import com.summer.demo.Entity.Meeting;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class SearchController {
    @Autowired
    private MeetingRepository meetingRepo;

    @GetMapping(value = "/search/meeting")
    public List<Meeting> getByMeetingTitle(@RequestParam("keyword") String keyword, @RequestParam("page") int page, @RequestParam("size") int size){
        List<Meeting> meetingList=new ArrayList<>();
        if( keyword!=""&&keyword!=null )
        {
            Pageable pageable=new PageRequest(page,size, Sort.Direction.ASC,"meetingId");
            meetingList=meetingRepo.findMeetingsByTitleContaining(keyword, pageable).getContent();
        }
        return meetingList;
    }
}
