package com.caihao.fileuploaddemo.model.usermodel;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author CaiHao
 * @create 2019-06-16 17:26:20
 */
public class User implements Serializable {

    private  String username;
    private MultipartFile headpic;
    private String localtion;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MultipartFile getHeadpic() {
        return headpic;
    }

    public void setHeadpic(MultipartFile headpic) {
        this.headpic = headpic;
    }

    public String getLocaltion() {
        return localtion;
    }

    public void setLocaltion(String localtion) {
        this.localtion = localtion;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", headpic=" + headpic +
                ", localtion='" + localtion + '\'' +
                '}';
    }
}
