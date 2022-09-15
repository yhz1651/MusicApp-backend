package com.example.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tool.DATA_BASE;

import java.sql.SQLException;
import java.text.DecimalFormat;

@Controller
public class RejisterController {
    @ResponseBody
    @RequestMapping("/register")
    public String rejister(@RequestParam(value = "u_username")String u_sername
            ,@RequestParam(value = "u_password")String password
            ,@RequestParam(value = "u_sex")String u_sex
            ,@RequestParam(value = "u_age")String u_age
            ,@RequestParam(value = "u_phone")String u_phone
            ,@RequestParam(value = "u_hobby")String u_hobby) throws SQLException {
        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT *" + " FROM UserList "+ "WHERE u_username='"+u_sername+"'for json auto"); // 是否注册
        if (st!=null){
            System.out.println("false");
            return "false"; // 没有注册
        }
        String num = db.select("select TOP 1 u_id FROM UserList ORDER BY u_id DESC");
        DecimalFormat decimalFormat = new DecimalFormat("00000000000");
        String m2= decimalFormat .format(Integer.parseInt(num.substring(1))+1);
        String new_id = "U"+m2;
        String insert_sql="INSERT INTO UserList VALUES('"+new_id+"','"+u_sername+"','"+password+"','"+u_sex+"',"+u_age+",'"+u_phone+"',0,'"+u_hobby+"');";
        db.exec(insert_sql);
        System.out.println(new_id);
        return new_id;
    }
    @ResponseBody
    @RequestMapping("/login")
    public String login(@RequestParam(value = "u_username")String u_sername
            ,@RequestParam(value = "u_password")String password
           ) throws SQLException {
        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT u_id" + " FROM UserList "+ "WHERE u_username='"+u_sername+"' AND u_password ='"+password+"'"); // 是否登录
        if (st==null){
            System.out.println("false");
            return "false"; // 登录失败
        }
        System.out.println(st);
        return st;
    }
}
