package cn.edu.hust.controller;

import cn.edu.hust.domain.*;
import cn.edu.hust.service.*;
import cn.edu.hust.service.jedis.RedisUtils;
import cn.edu.hust.utils.CommonUtils;
import cn.edu.hust.utils.Constant;
import cn.edu.hust.utils.domain.ResponseMsg;
import cn.edu.hust.utils.json.NoteJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private NoteBookUserService noteBookUserService;

    @Autowired
    private NotesService notesService;

    @Autowired
    private TagNoteService tagNoteService;

    @Autowired
    private VersionsService versionsService;

    @Autowired
    private TagsService tagsService;

    @RequestMapping("showAllNotes")
    public @ResponseBody
    ResponseMsg<List<NoteJson>> showAllNotes(HttpServletRequest request)
    {
        String key=CommonUtils.showCookies(request, Constant.COOKIE_NAME);
        Users user=(Users) redisUtils.get(key);
        //1.根据用户id查询noteBooksUser
        //1.1 现在redis中查询，如果redis中没有，那就在数据库中查询
        List<NoteBookUser> noteBookUserList=new ArrayList<>();
        List<String> noteBookIds=new ArrayList<>();
        Long noteBooKUserListSize=redisUtils.lGetListSize(user.getId()+":"+Constant.NOTEBOOKUSER_SUFFIX);
        if(noteBooKUserListSize==0)
        {
            noteBookUserList=this.noteBookUserService.findNoteBookUserListByUID(user.getId());
            //1.2将从数据库中查询的数据放入redis
            for(NoteBookUser noteBookUser:noteBookUserList)
            {
                redisUtils.lSet(user.getId()+":"+Constant.NOTEBOOKUSER_SUFFIX,noteBookUser);
            }
        }else
        {
            noteBookUserList= Arrays.asList(redisUtils.lGet(user.getId()+":"+Constant.NOTEBOOKUSER_SUFFIX,0,noteBooKUserListSize).toArray(new NoteBookUser[0]));
        }

        List<Notes> notesList=new ArrayList();
        //2.根据noteBookUserList的size不为空，那么根据notebook_id查询Notes
        //2.1redis中查询notes，如果没有就数据库查询
        if(noteBookUserList.size()!=0)
        {
            for(int i=0;i<noteBookUserList.size();i++)
                noteBookIds.add(noteBookUserList.get(i).getNotebook_id());
            Long notesSize=redisUtils.lGetListSize(user.getId()+":"+Constant.NOTES_SUFFIX);
            //2.2判断notes
            if(notesSize==0)
            {
                //在redis中查询不到，在数据库中查询放入redis
                notesList=this.notesService.findNotesListByNBId(noteBookIds);
                for(Notes notes:notesList) redisUtils.lSet(user.getId()+":"+Constant.NOTES_SUFFIX,notes);
            }
            else
            {
                notesList=Arrays.asList(redisUtils.lGet(user.getId()+":"+Constant.NOTES_SUFFIX,0,notesSize).toArray(new Notes[0]));

            }
        }

        //3.根据Noteslist中的notes查询tagsNote
        List<TagNote> tagNoteList=new ArrayList<TagNote>();
        List<String> notesIds=new ArrayList<>();
        List<String> versionIds=new ArrayList();
        if(notesList.size()!=0)
        {
            for(int i=0;i<notesList.size();i++)
            {
                notesIds.add(notesList.get(i).getId());
                versionIds.add(notesList.get(i).getVersion_id());
            }
            //3.1判断redis中是否有tags
            Long tagsNoteSize=redisUtils.lGetListSize(user.getId()+":"+Constant.TAGNOTE_SUFFIX);
            if(tagsNoteSize==0)
            {
                tagNoteList=this.tagNoteService.findTagNoteListByNId(notesIds);
                for(TagNote tagNote:tagNoteList) redisUtils.lSet(user.getId()+":"+Constant.TAGNOTE_SUFFIX,tagNote);
            }
            else
                tagNoteList=Arrays.asList(redisUtils.lGet(user.getId()+":"+Constant.TAGNOTE_SUFFIX,0,tagsNoteSize).toArray(new TagNote[0]));
        }

        //根据TagsNote查询相关的Tags
        List<Tags> tagsList=new ArrayList<Tags>();
        List<String> tagIds=new ArrayList();
        if(tagNoteList.size()!=0)
        {
            for(int i=0;i<tagNoteList.size();i++)
                tagIds.add(tagNoteList.get(i).getTag_id());
            Long tagsSize=redisUtils.lGetListSize(user.getId()+":"+Constant.TAGS_SUFFIX);
            if(tagsSize==0)
            {
                tagsList=this.tagsService.findTagsListByids(tagIds);
                for(Tags tags:tagsList) redisUtils.lSet(user.getId()+":"+Constant.TAGS_SUFFIX,tags);
            }
            else
                tagsList=Arrays.asList(redisUtils.lGet(user.getId()+":"+Constant.TAGS_SUFFIX,0,tagsSize).toArray(new Tags[0]));

        }

        List<Versions> versionsList=new ArrayList<>();
        //4.根据Noteslist中的version_id 查询Version
        if(notesList.size()!=0)
        {
            Long versionListSize=redisUtils.lGetListSize(user.getId()+":"+Constant.VERSION_SUFFIX);
            if(versionListSize==0)
            {
                versionsList=this.versionsService.findVersionsListByVIds(versionIds);
                for (Versions versions:versionsList) redisUtils.lSet(user.getId()+":"+Constant.VERSION_SUFFIX,versions);
            }
            else
                versionsList=Arrays.asList(redisUtils.lGet(user.getId()+":"+Constant.VERSION_SUFFIX,0,versionListSize).toArray(new Versions[0]));
        }

        HashMap<String,Versions> versionHashMap=new HashMap<>();
        for(Versions versions:versionsList)
        {
            versionHashMap.put(versions.getId(),versions);
        }

        HashMap<String,List<String>> tagNoteMap=new HashMap<>();
        for(TagNote tagNote:tagNoteList)
        {
            if(tagNoteMap.get(tagNote.getNote_id())==null)
            {
                List<String> s=new ArrayList<>();
                s.add(tagNote.getTag_id());
                tagNoteMap.put(tagNote.getNote_id(),s);
            }
            else
            {
                List<String> s=tagNoteMap.get(tagNote.getNote_id());
                s.add(tagNote.getTag_id());
                tagNoteMap.put(tagNote.getNote_id(),s);
            }
        }

        HashMap<String,Tags> tagsHashMap=new HashMap();
        for(Tags tags:tagsList)
        {
            tagsHashMap.put(tags.getId(),tags);
        }

        ResponseMsg<List<NoteJson>> responseMsg=new ResponseMsg<>();
        responseMsg.setResponse(new ArrayList<NoteJson>());

        responseMsg.setSuccess(true);

        for(Notes note:notesList)
        {
            NoteJson noteJson=new NoteJson();
            noteJson.setCreated_at(note.getCreated_at());
            noteJson.setUpdated_at(note.getUpdated_at());
            noteJson.setId(note.getId());
            noteJson.setNotebook_id(note.getNotebook_id());
            noteJson.getUsers().add(user);
            noteJson.setVersion_id(note.getVersion_id());
            Versions versions=versionHashMap.get(note.getVersion_id());
            versions.setUser(user);
            noteJson.setVersion(versions);

            List<String> tagIDs=tagNoteMap.get(note.getId());
            for(String tid:tagIDs)
            {
                noteJson.getTags().add(tagsHashMap.get(tid));
            }
            responseMsg.getResponse().add(noteJson);
        }
        return responseMsg;

    }

}
