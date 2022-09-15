package com.example.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tool.DATA_BASE;
import java.sql.SQLException;
import java.text.DecimalFormat;

@Controller
public class CommentController {
    // 添加新评论
    @ResponseBody
    @RequestMapping("/addComment")
    public String addComment(@RequestParam(value = "m_name")String m_name
                            ,@RequestParam(value = "c_userid")String c_userid
                            ,@RequestParam(value = "c_content")String c_content)
                            throws SQLException {
        DATA_BASE db = new DATA_BASE();
        String m2,musicid,commentid,num,insert_sql;
        musicid= db.select("select Music.m_id from Music where Music.m_name='"+m_name+"'");
        num= db.select("SELECT TOP 1 Comment.c_id FROM Comment ORDER BY Comment.c_id DESC");
        DecimalFormat decimalFormat = new DecimalFormat("00000000000");

        if(num.isEmpty()) m2= decimalFormat.format(1);
        else m2= decimalFormat.format(Integer.parseInt(num.substring(1))+1);

        commentid = "C"+m2;
        insert_sql="insert into Comment values('"+commentid+"','"+c_content+"','"+c_userid+"',0);";
        db.exec(insert_sql);
        insert_sql= "insert into Music_commment values('"+musicid+"','"+commentid+"');";
        db.exec(insert_sql);

        System.out.println(commentid);
        return commentid;
    }

    // 给一条评论点赞
    // 通过评论id(c_id)查询
    @ResponseBody
    @RequestMapping("/addLike")
    public String addLike(@RequestParam(value = "c_id")String c_id
                         ,@RequestParam(value = "c_good")String c_good)
                         throws SQLException {
        DATA_BASE db = new DATA_BASE();
        int good=Integer.parseInt(c_good)+1;
        String sql = "UPDATE Comment SET c_good = '"+good+"' WHERE c_id='"+c_id+"'";
        db.exec(sql);
        return "change";
    }

    // 查询一首歌的所有评论
    @ResponseBody
    @RequestMapping("/askComment")
    public String askComment(@RequestParam(value = "m_name")String m_name) throws SQLException {
        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT * FROM(select c_id,c_content,c_good,c_userid,u_username as c_username from Music,Comment,Music_commment,UserList where Music.m_name='"+m_name+"' and Music.m_id=Music_commment.mc_musicid and Comment.c_id=Music_commment.mc_commentid and UserList.u_id=Comment.c_userid) AS A for json auto");
        return st;
    }
}
