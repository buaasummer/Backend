package com.summer.demo.Controller;


import com.summer.demo.AssitClass.CustomizedAuthor;
import com.summer.demo.AssitClass.CustomizedPaper;
import com.summer.demo.Entity.Author;
import com.summer.demo.Entity.Meeting;
import com.summer.demo.Entity.Paper;
import com.summer.demo.Entity.PersonalUser;
import com.summer.demo.Repository.AuthorRepository;
import com.summer.demo.Repository.MeetingRepository;
import com.summer.demo.Repository.PaperRepository;
import com.summer.demo.Repository.PersonalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@CrossOrigin
@RestController
public class PaperController {
    private String filePath="C:\\Users\\Administrator\\Documents\\summer\\src\\main\\resources\\resources\\certify_file\\";
    @Autowired
    PaperRepository paperRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    PersonalUserRepository personalUserRepository;

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping(value="/paper/display")
    public List<Paper> paperDisplay(@RequestParam("meetingId") int meetingId, @RequestParam("userId") int uerId)
    {
        Meeting meeting=meetingRepository.findByMeetingId(meetingId);
        PersonalUser personalUser=personalUserRepository.findByUserId(uerId);
        return paperRepository.findPapersByPersonalUserAndMeeting(personalUser,meeting);
    }
    @GetMapping(value="/getAuthor")
    public Author getAuthor(@RequestParam("authorId") int authorId)
    {
        return authorRepository.findByAuthorId(authorId);
    }
    @PostMapping(value ="/paper/submission")
    public int paperSubmit(@RequestParam("userId") int userId,@RequestParam("meetingId")int meetingId,
                            CustomizedPaper customizedPaper)
    {
        Meeting meeting=meetingRepository.findByMeetingId(meetingId);
        PersonalUser personalUser=personalUserRepository.findByUserId(userId);
        Paper paper=new Paper();
        paper.setMeeting(meeting);
        paper.setPersonalUser(personalUser);
        int paperId=paper.getPaperId();
        String title=customizedPaper.getTitle();
        MultipartFile multipartFile=customizedPaper.getMultipartFile();
        String paperAbstract=customizedPaper.getPaperAbstract();
        paper.setTitle(title);
        paper.setPaperAbstract(paperAbstract);
        BufferedOutputStream stream;
        if(!multipartFile.isEmpty())
        {
            try {
                byte[] bytes = multipartFile.getBytes();
                String pathName=filePath+multipartFile.getOriginalFilename();
                File saveFile=new File(pathName);
                stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return -1 ;//提交论文
            }
        }
        paperRepository.save(paper);
        System.out.println(paper.getPaperId());
        return paperId;
    }
    @CrossOrigin
    @PostMapping(value = "/paper/author")
    public Boolean authorSubmit(@RequestParam("paperId") int paperId,List<CustomizedAuthor> customizedAuthorList)
    {
        Paper paper=paperRepository.getOne(paperId);
        if(paper==null)
            return false;
        String authorIds="";
        if(customizedAuthorList==null)
            return false;
        for(int i=0;i<customizedAuthorList.size();i++)
        {
            Author author=new Author();
            author.setAuthorName(customizedAuthorList.get(i).getAuthorName());
            author.setIdentificationNumber(customizedAuthorList.get(i).getIdentificationNumber());
            author.setOrganization(customizedAuthorList.get(i).getOrganization());
            authorRepository.save(author);
            if(i!=customizedAuthorList.size()-1)
            authorIds+=author.getAuthorId()+",";
            else
                authorIds+=author.getAuthorId()+"";
        }
        paper.setAuthorIds(authorIds);
        paperRepository.save(paper);
        return true;
    }

}
