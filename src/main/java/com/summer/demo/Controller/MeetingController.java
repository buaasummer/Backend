package com.summer.demo.Controller;

import com.summer.demo.AssitClass.Auditors;
import com.summer.demo.AssitClass.CusParticipants;
import com.summer.demo.Entity.*;
import com.summer.demo.Repository.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.summer.demo.StaticClass.DateParser;

import com.summer.demo.AssitClass.AssitMeeting;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class MeetingController {
    private String filePath="C:\\Users\\Administrator\\Documents\\release1\\upload\\paper_model\\";
    private String url="http://154.8.211.55:8081/";
    @Autowired
    private MeetingRepository meetingRepo;

    @Autowired
    private InstitutionRepository institutionRepo;

    @Autowired
    private PersonalUserRepository personalUserRepo;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantsRepository participantsRepository;

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

        if (assitMeeting.getFile()!=null||!assitMeeting.getFile().isEmpty()){
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
    @PostMapping(value = "/meeting/Register/uploadFile")
    public Boolean registerUpload(@RequestParam("participantsId") int participantsId
                            , @RequestParam("file")MultipartFile file)
    {
        Participants participants=participantsRepository.getOne(participantsId);
        BufferedOutputStream stream;
        String downloadUrl="";
        if(!file.isEmpty())
        {
            try {
                byte[] bytes = file.getBytes();
                String pathName="upload/charge/"+file.getOriginalFilename();
                downloadUrl=url+"charge/"+file.getOriginalFilename();
                File saveFile=new File(pathName);
                stream = new BufferedOutputStream(new FileOutputStream(saveFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
                participants.setDownloadUrl(downloadUrl);
            } catch (Exception e) {
                e.printStackTrace();
                return false ;
            }
        }
        participantsRepository.save(participants);
        return true;
    }
    @PostMapping(value = "/meeting/auditRegister/information")
    public int auditorRegister(@RequestParam(value = "userId") int userId,
                               @RequestParam(value = "meetingId") int meetingId,
                               @RequestParam(value = "participants") String attenders)
    {
        Participants participants=Register(userId,meetingId,attenders);
        participants.setPaperNumber(0);
        participantsRepository.save(participants);
        return participants.getParticipantsId();
    }
    @PostMapping(value = "/meeting/authorRegister/information")
    public int authorRegister(@RequestParam(value = "userId") int userId,
                                      @RequestParam(value = "meetingId") int meetingId
                                        , @RequestParam(value = "number") int number
                                        , @RequestParam(value = "participants") String attenders){
        Paper paper=paperRepository.findPaperByMeetingAndNumber(meetingRepo.findByMeetingId(meetingId),number);
        if(paper.getStatus()!=1&&paper.getStatus()!=2)
            return -1;//您目前还没有被该会议录用的论文
        Participants participants=Register(meetingId,userId,attenders);
        participants.setPaperNumber(number);
        participantsRepository.save(participants);
        return participants.getParticipantsId();
    }
    @GetMapping(value = "/meeting/getAuthorRegisterInfo")
    public List<CusParticipants> getRegisterInfo(@RequestParam("meetingId") int meetingId)
    {
        Meeting meeting=meetingRepo.findByMeetingId(meetingId);
        List<Participants> participantsList=participantsRepository.findAuthorParticipants(meetingId);
        List<CusParticipants> cusParticipantsList=new ArrayList<>();
        for(int i=0;i<participantsList.size();i++)
        {
            Participants participants=participantsList.get(i);
            Paper paper=paperRepository.findPaperByMeetingAndNumber(meeting,participants.getPaperNumber());
            CusParticipants cusParticipants=new CusParticipants();
            cusParticipants.setPaperNumber(participants.getPaperNumber());
            cusParticipants.setDownloadUrl(participants.getDownloadUrl());
            cusParticipants.setPaperTitle(paper.getTitle());
            String participantIdList=participants.getParticipantIdList();
            String[] participantIds=participantIdList.split(",");
            String names="";
            String genders="";
            String emails="";
            String bookAccommodations="";
            for(int j=0;j<participantIds.length;j++)
            {
                int participantId=Integer.parseInt(participantIds[j]);
                Participant participant=participantRepository.getOne(participantId);
                if(j!=participantIds.length-1) {
                    names += participant.getName() + ",";
                    genders += participant.getGender() + ",";
                    emails += participant.getEmail() + ",";
                    bookAccommodations += participant.getBookAccommodation() + ",";
                }
                else
                {
                    names += participant.getName();
                    genders += participant.getGender();
                    emails += participant.getEmail();
                    bookAccommodations += participant.getBookAccommodation();
                }
            }
            cusParticipants.setNames(names);
            cusParticipants.setGenders(genders);
            cusParticipants.setEmails(emails);
            cusParticipants.setBookAccommodations(bookAccommodations);
            cusParticipantsList.add(cusParticipants);
        }
        return cusParticipantsList;
    }
    @GetMapping(value = "/meeting/auditorRegisterInfo")
    public List<Auditors> getAuditorsInfo(@RequestParam("meetingId") int meetingId)
    {
        Meeting meeting=meetingRepo.findByMeetingId(meetingId);
        List<Participants> participantsList=participantsRepository.findAuditParticipants(meetingId);
        List<Auditors> auditorsList=new ArrayList<>();
        for(int i=0;i<participantsList.size();i++)
        {
            Participants participants=participantsList.get(i);
            Auditors auditors=new Auditors();
            auditors.setDownloadUrl(participants.getDownloadUrl());
            String participantIdList=participants.getParticipantIdList();
            String[] participantIds=participantIdList.split(",");
            String names="";
            String genders="";
            String emails="";
            String bookAccommodations="";
            for(int j=0;j<participantIds.length;j++)
            {
                int participantId=Integer.parseInt(participantIds[j]);
                Participant participant=participantRepository.getOne(participantId);
                if(j!=participantIds.length-1) {
                    names += participant.getName() + ",";
                    genders += participant.getGender() + ",";
                    emails += participant.getEmail() + ",";
                    bookAccommodations += participant.getBookAccommodation() + ",";
                }
                else
                {
                    names += participant.getName();
                    genders += participant.getGender();
                    emails += participant.getEmail();
                    bookAccommodations += participant.getBookAccommodation();
                }
            }
            auditors.setNames(names);
            auditors.setGenders(genders);
            auditors.setEmails(emails);
            auditors.setBookAccommodations(bookAccommodations);
            auditorsList.add(auditors);
        }
        return auditorsList;
    }


    public Participants Register(int meetingId,int uerId,String attenders)
    {
        JSONArray jsonArray=JSONArray.fromObject(attenders);
        Object[] participantList=jsonArray.toArray();
        Participants participants=new Participants();
        PersonalUser personalUser=personalUserRepo.findByUserId(uerId);
        Meeting meeting=meetingRepo.findByMeetingId(meetingId);
        participants.setMeeting(meeting);
        participants.setPersonalUser(personalUser);
        String participantIds="";
        for(int i=0;i<participantList.length;i++)
        {
            JSONObject jsonObject=JSONObject.fromObject(participantList[i]);
            Participant participant=new Participant();
            participant.setName(jsonObject.getString("name"));
            participant.setGender(jsonObject.getString("gender"));
            participant.setEmail(jsonObject.getString("email"));
            participant.setBookAccommodation(jsonObject.getString("bookAccommodation"));
            participantRepository.save(participant);
            if(i!=participantList.length-1)
            {
                participantIds+=participant.getParticipantId()+",";
            }
            else
                participantIds+=participant.getParticipantId();
        }
        participants.setParticipantIdList(participantIds);
        participantsRepository.save(participants);
        return participants;
    }





}
