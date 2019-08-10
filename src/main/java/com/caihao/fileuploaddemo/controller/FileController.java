package com.caihao.fileuploaddemo.controller;

import ch.qos.logback.core.util.FileUtil;
import com.caihao.fileuploaddemo.model.usermodel.User;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.URLEncoder;

/**
 * @author CaiHao
 * @create 2019-06-16 12:18:00
 */
@Controller
public class FileController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "你好";
    }


    @RequestMapping("/success")
    public String success(){
        return "success";
    }


    @RequestMapping("/success2")
    public String success2(){
        return "suc/success2";
    }

    /*来到上传文件的界面*/
    @RequestMapping("/upfile")
    public String upfile(){
        return "fileupload/upfile";
    }

    /*用户注册*/
    @RequestMapping("/loginuser")
    public String loginuser(HttpServletRequest request, User user, Model model) throws Exception{

        if(user != null){
            System.out.println("name = ===="+user.getUsername());

            if(!user.getHeadpic().isEmpty()){
                //项目部署的位置
                String path = request.getServletContext().getRealPath("/upload/");

                //自定义文件位置
                //String path = "G:\\up\\upload\\";
                System.out.println("path===="+path);

                //上传的文件名
                String filename = user.getHeadpic().getOriginalFilename();
                System.out.println("filenamre====="+filename);

                //上传文件的物理位置
                File filepath = new File(path, filename);
                System.out.println("filepath======"+filepath);

                if (!filepath.getParentFile().exists()){
                    //如果不存在文件位置，则创建文件目录
                    boolean mkdirs = filepath.getParentFile().mkdirs();
                    System.out.println("mkdirs===="+mkdirs);
                }

                //将目标文件保存到设定的文件位置
                user.getHeadpic().transferTo(new File(path+File.separator+filename));

                user.setLocaltion(filepath.toString());
                System.out.println("最后的user====="+user);
                model.addAttribute("user",user);

                return "fileupload/success";
            }else {
                return "fileupload/error";
            }
        }else{
            return "fileupload/error";
        }
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, @RequestParam("filename") String filename,
                                   @RequestHeader("User-agent") String userAgent,Model model) throws Exception{


        System.out.println("filename====="+filename);
        //文件下载路径
        String path = request.getServletContext().getRealPath("/upload/");
        //构建file
        File file = new File(path + File.separator + filename);
        //ok表示http中的状态码200
        ResponseEntity.BodyBuilder builder = ResponseEntity.ok();
        //内容长度
        ResponseEntity.BodyBuilder contentLength = builder.contentLength(file.length());
        //获取二进制流数据
        ResponseEntity.BodyBuilder stream = builder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        //对文件名进行解码
        filename = URLEncoder.encode(filename,"utf-8");


        if(userAgent.indexOf("MSIE") > 0){
            builder.header("Content-Disposition","attachment;filename="+filename);
        }else {
            builder.header("Content-Disposition","attachment;filename*=utf-8''"+filename);
        }

        //return builder.body(FileUtils.readFileToByteArray(file));
        return builder.body(org.apache.commons.io.FileUtils.readFileToByteArray(file));
    }
}
