package cn.edu.hust.controller;

import cn.edu.hust.domain.*;
import cn.edu.hust.service.UsersService;
import cn.edu.hust.utils.CommonUtils;
import cn.edu.hust.utils.domain.ResponseMsg;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Version;

import java.sql.Timestamp;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UsersService usersService;
    /**
     * 1.接受页面传递的参数,参数的具体类型有username、fristname、lastname、password、language.
        2.检查邮箱(username)是否注册，如果注册告知用户已经注册;否则进行第三步
        3.封装数据，将数据插入数据库，保存的数据包含Users、Setting、NoteBook、NoteBookUser、Notes、TagNote、Versions、Tags、NoteUser
        4.注册成功返回登录后的页面
     * @param username
     * @param firstname
     * @param lastname
     * @param password
     * @param ui_language
     * @param admin_creator
     * @param _token
     * @return
     */
    @RequestMapping("/register")
    public String register(Model model, @RequestParam String username, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String password, @RequestParam String ui_language, @RequestParam String admin_creator, @RequestParam String _token)
    {
        Users users=this.usersService.findUsersByUserName(username);
        ResponseMsg<String> responseMsg=new ResponseMsg<>();
        if(users!=null)
        {
            responseMsg.setStatus(302);
            responseMsg.setResponse("邮件已经注册!!!");
            model.addAttribute("user",users);
            model.addAttribute("responseMsg",responseMsg);
            return "user/regist";
        }
        users = param2Users(username, firstname, lastname, password, admin_creator, _token);

        //封装Setting
        Settings settings=param2Settings(ui_language, users);


        //封装NoteBooks
        NoteBooks noteBooks = param2NoteBooks();

        //封装NoteBookUser
        NoteBookUser noteBookUser = param2NoteBookUser(users, noteBooks);

        //封装Versions
        Versions versions = param2Versions(users.getId());

        //封装Notes
        Notes notes = param2Note(noteBooks, versions);

        //封装NotesUser
        NoteUser noteUser=new NoteUser(users.getId(),notes.getId(),noteBookUser.getUmask());

        //封装Tags
        Tags tags = param2Tag(users.getId());

        //封装TagNote
        TagNote tagNote=param2TagNote(notes.getId(), tags.getId());

        this.usersService.createUser(users,settings,noteBooks,noteBookUser,versions,notes,noteUser,tags,tagNote);

        responseMsg.setStatus(200);

        responseMsg.setResponse("注册成功,正在跳转登录!!!");
        model.addAttribute("user",users);
        model.addAttribute("responseMsg",responseMsg);
        return "home/main";
    }


    @RequestMapping("/login")
    public String login()
    {
        return "note/main";
    }
    private Notes param2Note(NoteBooks noteBooks, Versions versions) {
        String noteId= UUID.randomUUID().toString().replace("-","");
        Notes notes=new Notes(noteId,noteBooks.getId(),versions.getId());
        return notes;
    }

    private Versions param2Versions(String user_id) {
        String version_id= UUID.randomUUID().toString().replace("-","");
        String previous_id=null;
        String next_id=null;
        String version_title="Welcome to LavNote";
        String content_preview="Hi there, welcome to LavNote, the open source note-taking solution. This\n" +
                "        is your first note and we&#39;d like to explain to you some features we hope you&#39;ll find very\n" +
                "        interesting.NotesNotes are the basic form of writing in LavNote.";
        String content="<p>Hi there, <strong>welcome to LavNote</strong>, the open source note-taking solution. This\n" +
                "        is your first note and we&#39;d like to explain to you some features we hope you&#39;ll find very\n" +
                "        interesting.</p><h3><strong>Notes</strong></h3><p>Notes are the basic form of writing in LavNote.\n" +
                "        Notes are saved inside notebooks. You already have one named &#39;Welcome&#39; in which this note is\n" +
                "        stored. You can create as much notebooks and notes as you want by using the <a id=\"file_click_handler\" href=\"#\">\n" +
                "        File menu</a>.</p><h3><strong>Attachments</strong></h3><p>You can also add attachments to your notes.\n" +
                "        This allows you to embed important information and have it there when you need it. Just click its link,\n" +
                "        and you&#39;ll be sent to the file. And since LavNote is self hosted, you have better privacy too.</p>\n" +
                "        <h3><strong>Tags</strong></h3><p>Tags are one of LavNote&#39;s powerful features. Tags allow you to search\n" +
                "        for related notes but which are not in the same notebook. Do you have an idea and want to save it for later now?\n" +
                "        Put it in a notebook (like &#39;dinner cooking&#39;) and tag it with cooking. Later, when you&#39;ve got time you\n" +
                "        can just click on cooking from the sidebar and all cooking-related notes are there, no matter if they&#39;re for lunch,\n" +
                "        dinner or breakfast.</p><h3><strong>Search</strong></h3><p>If you want to search with a more detailed criteria, you can\n" +
                "        use LavNote&#39;s search feature. Just <a id=\"search_handler\" href=\"#\">write your query</a> and LavNote will show you the results from\n" +
                "        all your notes, no matter in which notebook they are or tag they have.</p><h3><strong>Version Control</strong></h3><p>Everytime\n" +
                "        you save a note in LavNote, a new version is created. This feature allows you to be able to see what changed throughout the\n" +
                "        life of your note in a detailed manner.</p><h3><strong>Shareable</strong></h3><p>Each note you write on LavNote can be shared\n" +
                "        with any other user of LavNote. Are you a company employee and would like to share those draft guidelines for that project? No\n" +
                "        worries, just sare the note with those users you want to share it with while keeping total control of your note. You can control\n" +
                "        who sees and edits the note and you can also who did what thanks to version control.</p><p>We hope that you like your experience\n" +
                "        with LavNote. If you have any feedback or encounter any issues, just submit an issue <a href=\"https://github.com/twostairs/LavNote/issues/new\">\n" +
                "        on Github</a>. If you want to be informed on what we&#39;re planning for LavNote and be able to participate in the discussion, please join\n" +
                "        our mailing list <a href=\"mailto:LavNote-dev@googlegroups.com\">here</a>.</p><p><strong>Thanks for choosing LavNote,</strong></p><p><strong>\n" +
                "        The LavNote Team. </strong></p>'";

        Versions versions=new Versions(version_id,previous_id,next_id,version_title,content_preview,user_id,content);
        return versions;
    }

    private NoteBookUser param2NoteBookUser(Users users, NoteBooks noteBooks) {
        byte umask=7;
        NoteBookUser noteBookUser=new NoteBookUser(users.getId(),noteBooks.getId(),umask);
        return noteBookUser;
    }

    private NoteBooks param2NoteBooks() {
        String noteBookId= UUID.randomUUID().toString().replace("-","");
        String parentId=null;
        byte type=0;
        String notebook_title="Welcome";
        NoteBooks noteBooks=new NoteBooks(noteBookId,parentId,type,notebook_title);
        return noteBooks;
    }


    private Settings param2Settings(@RequestParam String ui_language, Users users) {
        String setting_id= UUID.randomUUID().toString().replace("-","");
        Settings settings=new Settings(setting_id,users.getId(),ui_language);
        return settings;
    }

    private Users param2Users(@RequestParam String username, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String password, @RequestParam String admin_creator, @RequestParam String _token) {
        //封装Users
        String id= UUID.randomUUID().toString().replace("-","");
        String passwd= CommonUtils.encoderByMD5(password);
        byte is_admin=0;
        if(StringUtils.isNotBlank(admin_creator)) is_admin=1;
        String remember_token=_token;
        Users bean=new Users(id,username,passwd,firstname,lastname,is_admin,remember_token);
        return bean;
    }

    private Tags param2Tag(String id) {
        //封装Tags
        String tag_id= UUID.randomUUID().toString().replace("-","");
        byte visibility=0;
        String title="welcome";
        String user_id=id;
        String parent_id=null;
        Tags tags=new Tags(tag_id,visibility,title,user_id,parent_id);
        return tags;
    }


    private TagNote param2TagNote(String noteId, String tag_id) {
        //封装TagNote
        TagNote tagNote=new TagNote(noteId,tag_id);
        return tagNote;
    }
}
