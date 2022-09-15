package com.example.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tool.DATA_BASE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;

@Controller
public class AskControler {
    @ResponseBody
    @PostMapping(value="/askMusic" ,produces = "application/json;charset=utf-8")
    public static String getmusic() throws FileNotFoundException, SQLException {

        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT * FROM(SELECT Music.m_id,Music.m_name,s_name as m_singer,Music.m_url,Music.m_userid,Music.m_duration FROM Music,Singer WHERE Music.m_singer=Singer.s_id ) AS A for json auto");//是否已经存在
        return st;
    }


    @ResponseBody
    @PostMapping(value="/askSinger" ,produces = "application/json;charset=utf-8")
    public static String getsinger() throws FileNotFoundException, SQLException {

        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT * FROM Singer for json auto");//查找歌手表格
        return st;
    }

    @ResponseBody
    @PostMapping(value="/askList" ,produces = "application/json;charset=utf-8")
    public static String getlist() throws FileNotFoundException, SQLException {

        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT * FROM Singer for json auto");//查找
        return st;
    }

    @ResponseBody
    @PostMapping(value="/askUserList" ,produces = "application/json;charset=utf-8")
    public static String getuserlist() throws FileNotFoundException, SQLException {

        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT * FROM UserList for json auto");//查找
        return st;
    }

    @ResponseBody
    @PostMapping(value="/askUser" ,produces = "application/json;charset=utf-8")
    public static String getUser(@RequestParam(value = "u_id")String u_id) throws FileNotFoundException, SQLException {

        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT * FROM UserList WHERE u_id='"+u_id+"' for json auto");//获得用户信息
        st= st.replace("[","").replace("]","");
        System.out.println(st);
        return st;
    }
}
