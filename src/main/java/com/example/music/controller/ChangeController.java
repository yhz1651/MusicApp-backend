package com.example.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tool.DATA_BASE;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.DecimalFormat;

@Controller
public class ChangeController {
    @ResponseBody
    @PostMapping(value="/changesinger" ,produces = "application/json;charset=utf-8")
    public static String changesinger(@RequestParam(value = "s_id")String s_id,
                                      @RequestParam(value = "s_name")String s_name,
                                      @RequestParam(value = "s_region")String s_region,
                                      @RequestParam(value = "s_intro")String s_intro
                                      ) throws FileNotFoundException, SQLException {

        DATA_BASE db = new DATA_BASE();
        String sql = "UPDATE Singer SET s_name = '"+s_name+"',s_region =  '"+s_region +"',s_intro = '"+s_intro+"' WHERE s_id='"+s_id+"'";
        System.out.println(sql);
        db.exec(sql);

        return "change";
    }
    @ResponseBody
    @RequestMapping("/changeUser")
    public String rejister(@RequestParam(value = "u_id")String u_id
            ,@RequestParam(value = "u_username")String u_sername
            ,@RequestParam(value = "u_password")String password
            ,@RequestParam(value = "u_age")String u_age
            ,@RequestParam(value = "u_phone")String u_phone
            ,@RequestParam(value = "u_hobby")String u_hobby) throws SQLException {
        DATA_BASE db = new DATA_BASE();
        String insert_sql="UPDATE UserList SET u_username ='"+u_sername+"',u_password='"+password+"',u_age="+u_age+",u_phone='"+u_phone+"',u_hobby='"+u_hobby+"' WHERE u_id='"+u_id+"'";
        db.exec(insert_sql);
        System.out.println(insert_sql);
        return "ssss";
    }
}
