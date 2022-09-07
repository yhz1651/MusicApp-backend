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
public class FileUploadController {
    @ResponseBody
    @PostMapping("/solution")
    public static String fileUpload(@RequestParam(value = "originalData") MultipartFile file,
                                    @RequestParam(value = "m_name")String m_name,
                                    @RequestParam(value = "m_singer")String m_singer,
                                    @RequestParam(value = "m_userid")String m_userid,
                                    @RequestParam(value = "m_duration")String m_duration) throws FileNotFoundException, SQLException {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        DATA_BASE db = new DATA_BASE();
        String st= db.select("SELECT *" + " FROM Music "+ "WHERE m_name='"+ m_name +"' for json auto");//是否已经存在
        if (st!=null){
            System.out.println("false");
            return "false";//已存在
        }
        System.out.println("文件开始上传");
        String fileName =  file.getOriginalFilename();  // 文件名
        System.out.println(fileName);
        //上传到服务器之中了
//        String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
        String filePath="static/file/";
        File path = ResourceUtils.getFile("classpath:");
        File uploadDir = new File(path.getAbsolutePath(),"static/file/");
        File dest = new File(uploadDir.getAbsolutePath() +"\\"+ fileName);
        System.out.println("------------------------------------------");
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
            try{
                dest.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String urlpath = "http://192.168.1.110:7506/static/file/"+dest.getPath().substring(dest.getPath().lastIndexOf("\\") + 1);//存储路径
        DecimalFormat decimalFormat = new DecimalFormat("00000000000");//格式字符
        String id = db.select("select Top 1 m_id from Music ORDER BY m_id DESC");//获得当前歌曲编号
        String singer_id= db.select("SELECT s_id" + " FROM Singer "+ "WHERE s_name='"+ m_singer +"'");//歌手是否已经存在
        if (st!=null){
            System.out.println("false");
            return "false";//已存在
        }
        if (singer_id==null){//如果歌手信息不存在
            String pre_singer = db.select("SELECT Top 1 s_id" + " FROM Singer ORDER BY s_id DESC");//获得最高歌手标号
            String m1= decimalFormat .format(Integer.parseInt(pre_singer.substring(1))+1);
            singer_id =  "S"+m1;
            String sql1 = "insert into Singer(s_id,s_name) values('"+singer_id+"','"+m_singer+"')";
            db.exec(sql1);
            System.out.println("add"+singer_id);
        }
        String m2= decimalFormat .format(Integer.parseInt(id.substring(1))+1);
        String new_id =  "M"+m2;
        String sql="insert into Music(m_id,m_name,m_url,m_singer,m_type,m_userid,m_duration) values('"+new_id+"','"
                +m_name+"','"+urlpath+"','"+singer_id+"',0,'"+m_userid+"',"+m_duration+")";
        db.exec(sql);

        return urlpath;
    }

}
