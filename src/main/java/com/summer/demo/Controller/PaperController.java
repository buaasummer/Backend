package com.summer.demo.Controller;


import com.summer.demo.AssitClass.BigPaper;
import com.summer.demo.AssitClass.CustomizedAuthor;
import com.summer.demo.AssitClass.CustomizedPaper;
import com.summer.demo.AssitClass.ExpandPaper;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    JavaMailSender jms;

    String url="http://154.8.211.55:8081/";

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
        Boolean flag=updatePaper(paperId,customizedPaper);
        Paper paper=paperRepository.getOne(paperId);
        Meeting meeting=paper.getMeeting();
        int count=paperRepository.findPapersByMeeting(meeting).size();
        paper.setNumber(count);
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("m13121210685@163.com");
        simpleMailMessage.setTo(paper.getPersonalUser().getEmail());
        simpleMailMessage.setSubject(""+paper.getPersonalUser().getUsername()+
                "您已经成功在"+paper.getMeeting().getTitle()+"投稿");
        simpleMailMessage.setText("感谢您的投稿，您的论文题目是"+paper.getTitle()+"\n编号为"+paper.getNumber());
        jms.send(simpleMailMessage);
        paperRepository.save(paper);
        return flag;
    }

    @PostMapping(value="/paper/update")
    public Boolean paperUpdate(@RequestParam("paperId") int paperId,CustomizedPaper customizedPaper)
    {
        Boolean flag=updatePaper(paperId,customizedPaper);
        if(flag)
        {
            Paper paper=paperRepository.getOne(paperId);
            paper.setStatus(6);
            return true;
        }
        else
            return false;
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
            JSONObject jsonObject=JSONObject.fromObject(customizedAuthorList[i]);
            String email=jsonObject.getString("email");
            Author author=authorRepository.findByEmail(email);
            if(author==null) author=new Author();
            author.setAuthorName(jsonObject.getString("authorName"));
            author.setEmail(email);
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
    public List<ExpandPaper> meetingPaperDisplay(@PathVariable("meetingId") int meetingId)
    {
            Meeting meeting=meetingRepository.findByMeetingId(meetingId);
            List<Paper> paperList=paperRepository.findPapersByMeeting(meeting);
            List<BigPaper> bigPapers=displayPaper(paperList);
            List<ExpandPaper> expandPapers=new ArrayList<>();
            for(int i=0;i<bigPapers.size();i++)
            {
                ExpandPaper expandPaper=new ExpandPaper(bigPapers.get(i));
                int paperId=bigPapers.get(i).getPaperId();
                Paper paper=paperRepository.getOne(paperId);
                PersonalUser personalUser=paper.getPersonalUser();
                String email=personalUser.getEmail();
                expandPaper.setEmail(email);
                expandPapers.add(expandPaper);
            }
            return expandPapers;
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
    @PostMapping(value = "/paper/review")
    public void reviewPaper(@RequestParam("paperId")int paperId,@RequestParam("status") int status,
                            HttpServletRequest request)
    {
        Paper paper=paperRepository.getOne(paperId);
        int beforeStatus=paper.getStatus();
        if(beforeStatus==6&&status==1)
            paper.setStatus(2);
        else
            paper.setStatus(status);
        PersonalUser personalUser=paper.getPersonalUser();
        Meeting meeting=paper.getMeeting();
        String acceptSubject="恭喜您，尊敬的 "+personalUser.getUsername()+"学者您的论文投稿已经被"
                +meeting.getTitle()+"录用";
        String refuseSubject="很抱歉，尊敬的 "+personalUser.getUsername()+"学者您的论文投稿未被"
                +meeting.getTitle()+"录用";
        String modifySubject="尊敬的 "+personalUser.getUsername()+"学者您在会议"
                +meeting.getTitle()+"论文投稿需要修改才能被录用";
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("m13121210685@163.com");
        message.setTo(personalUserRepository.findByUserId(paper.getPersonalUser().getUserId()).getEmail());
        if(status==1)
        message.setSubject(acceptSubject);
        else if(status==5)
            message.setSubject(modifySubject);
        else if(status==4)
            message.setSubject(refuseSubject);
        message.setText(request.getParameter("mes"));
        jms.send(message);
        paperRepository.save(paper);
    }
    public Boolean updatePaper(int paperId,CustomizedPaper customizedPaper)
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
        return true;
    }
}
