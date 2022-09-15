package com.example.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tool.DATA_BASE;

import java.sql.SQLException;

@Controller
    public class MusicListController {
        @ResponseBody
        @RequestMapping("/getMusicList")
    public String hello() throws SQLException {
        DATA_BASE db = new DATA_BASE();
        String st= db.select("select * from MusicApp for json auto");
        return st;
    }
    @ResponseBody
    @RequestMapping("/searchMusic")
    public String search(@RequestParam(value = "m_name")String m_name) throws SQLException {
        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT * FROM(SELECT Music.m_id,Music.m_name,s_name as m_singer,Music.m_url,Music.m_userid,Music.m_duration FROM Music,Singer WHERE Music.m_singer=Singer.s_id AND Music.m_name like '%"+m_name+"%' ) AS A for json auto");
        return st;
    }

}
