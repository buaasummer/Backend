package com.summer.demo.Controller;


import com.summer.demo.AssitClass.BigPaper;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.dc.path.PathError;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class PaperController {
    @Autowired
    PaperRepository paperRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    PersonalUserRepository personalUserRepository;

    @Autowired
    AuthorRepository authorRepository;

    String url="http://0686ca8e.ngrok.io/";

    @GetMapping(value="/paper/display")
    public List<BigPaper> paperDisplay(@RequestParam("meetingId") int meetingId, @RequestParam("userId") int uerId)
    {
        Meeting meeting=meetingRepository.findByMeetingId(meetingId);
        PersonalUser personalUser=personalUserRepository.findByUserId(uerId);
        List<Paper> paperList=paperRepository.findPapersByPersonalUserAndMeeting(personalUser,meeting);
       return displayPaper(paperList);
    }
//    @GetMapping(value="/getAuthor")
//    public Author getAuthor(@RequestParam("authorId") int authorId)
//    {
//        return authorRepository.findByAuthorId(authorId);
//    }
    @PostMapping(value ="/paper/submission")
    public Boolean paperSubmit(@RequestParam("paperId") int paperId,
                            CustomizedPaper customizedPaper)
    {
        Paper paper=paperRepository.getOne(paperId);
        String title=customizedPaper.getTitle();
        MultipartFile multipartFile=customizedPaper.getMultipartFile();
        String paperAbstract=customizedPaper.getPaperAbstract();
        paper.setTitle(title);
        paper.setPaperAbstract(paperAbstract);
        BufferedOutputStream stream;
        String downloadUrl="";
        if(!multipartFile.isEmpty())
        {
            try {
                byte[] bytes = multipartFile.getBytes();
                String pathName="upload/papers/"+multipartFile.getOriginalFilename();
                downloadUrl=url+"papers/"+multipartFile.getOriginalFilename();
                File saveFile=new File(pathName);
                stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false ;//提交论文失败
            }
        }
        paper.setDownloadUrl(downloadUrl);
        paper.setStatus(3);
        paperRepository.save(paper);
        System.out.println(paper.getPaperId());
        return true;
    }
    @CrossOrigin
    @PostMapping(value = "/paper/author")
    public int authorSubmit(@RequestParam("userId") int userId, @RequestParam("meetingId") int meetingId,
                            @RequestParam("customizedAuthorList") String str)
    {
        JSONArray jsonArray=JSONArray.fromObject(str);
        Object[] customizedAuthorList=jsonArray.toArray();
        PersonalUser user=personalUserRepository.findByUserId(userId);
        Meeting meeting=meetingRepository.findByMeetingId(meetingId);
        Paper paper=new Paper();
        paper.setPersonalUser(user);
        paper.setMeeting(meeting);
        String authorIds="";
        System.out.println(customizedAuthorList.length);
        if(customizedAuthorList==null)
            return -1;
        for(int i=0;i<customizedAuthorList.length;i++)
        {
            Author author=new Author();
            JSONObject jsonObject=JSONObject.fromObject(customizedAuthorList[i]);
            author.setAuthorName(jsonObject.getString("authorName"));
            author.setIdentificationNumber(jsonObject.getInt("identificationNumber"));
            author.setOrganization(jsonObject.getString("organization"));
            authorRepository.save(author);
            if(i!=customizedAuthorList.length-1)
            authorIds+=author.getAuthorId()+",";
            else
                authorIds+=author.getAuthorId()+"";
        }
        paper.setAuthorIds(authorIds);
        paperRepository.save(paper);
        return paper.getPaperId();
    }
    @GetMapping(value = "/paper/{meetingId}")
    public List<BigPaper> meetingPaperDisplay(@PathVariable("meetingId") int meetingId)
    {
            Meeting meeting=meetingRepository.findByMeetingId(meetingId);
            List<Paper> paperList=paperRepository.findPapersByMeeting(meeting);
            return displayPaper(paperList);
    }
    @PostMapping(value = "/paper/author/update")
    public Boolean authorUpdate(@RequestParam("paperId") int paperId,@RequestParam("customizedAuthorList") String str)
    {
        JSONArray jsonArray=JSONArray.fromObject(str);
        Object[] customizedAuthorList=jsonArray.toArray();
        Paper paper=paperRepository.getOne(paperId);
        String authorIds="";
        System.out.println(customizedAuthorList.length);
        if(customizedAuthorList==null)
            return false;
        for(int i=0;i<customizedAuthorList.length;i++)
        {
            JSONObject jsonObject=JSONObject.fromObject(customizedAuthorList[i]);
            String identificationNumber=jsonObject.getString("identification");
            int number=Integer.parseInt(identificationNumber);
            Author author=authorRepository.findByIdentificationNumber(number);
            if(author==null) author=new Author();
            author.setAuthorName(jsonObject.getString("authorName"));
            author.setIdentificationNumber(jsonObject.getInt("identificationNumber"));
            author.setOrganization(jsonObject.getString("organization"));
            authorRepository.save(author);
            if(i!=customizedAuthorList.length-1)
                authorIds+=author.getAuthorId()+",";
            else
                authorIds+=author.getAuthorId()+"";
        }
        paper.setAuthorIds(authorIds);
        paperRepository.save(paper);
        return true;
    }

    public List<BigPaper> displayPaper(List<Paper> paperList)
    {
        List<BigPaper> bigPapers=new ArrayList<>();
        for(int i=0;i<paperList.size();i++)
        {
            Paper paper=paperList.get(i);
            BigPaper  bigPaper=new BigPaper();
            bigPaper.setPaperId(paper.getPaperId());
            bigPaper.setTitle(paper.getTitle());
            bigPaper.setPaperAbstract(paper.getPaperAbstract());
            bigPaper.setDownloadUrl(paper.getDownloadUrl());
            bigPaper.setStatus(paper.getStatus());
            String str=paper.getAuthorIds();
            String[] strings=str.split(",");
            String names="";
            String organizations="";
            for(int j=0;j<strings.length;j++)
            {
                int authorID=Integer.parseInt(strings[j]);
                Author author=authorRepository.findByAuthorId(authorID);
                if(j!=strings.length-1)
                {
                    names+=author.getAuthorName()+",";
                    organizations+=author.getOrganization()+",";
                }
                else
                {
                    names+=author.getAuthorName();
                    organizations+=author.getOrganization();
                }
            }
            bigPaper.setNames(names);
            bigPaper.setOrganizations(organizations);
            bigPapers.add(bigPaper);
        }
        return bigPapers;
    }
}
