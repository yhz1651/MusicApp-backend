package com.example.music.controller;

import com.example.music.object.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tool.DATA_BASE;

import java.sql.SQLException;
import java.util.List;

@Controller
public class HelloController {
    @ResponseBody
    @RequestMapping("/hello")
    public String hello() throws SQLException {
        DATA_BASE db = new DATA_BASE();
        String st= db.select("select * from UserList for json auto");
        jsonToList(st);

        return st;
    }
    public static void jsonToList(String json) {

        Gson gson = new Gson();
        List<User> list = gson.fromJson(json, new TypeToken<List<User>>() {}.getType());//对于不是类的情况，用这个参数给出
        for (User users : list) {
            System.out.println(users.getU_username()+" "+users.getU_password());
        }
    }
}