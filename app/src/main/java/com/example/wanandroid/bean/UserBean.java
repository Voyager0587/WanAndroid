package com.example.wanandroid.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @version 1.0
 * @className: UserBean
 * @author: Voyager
 * @description: TODO
 * @date: 2023/7/7 22:27
 **/
public class UserBean extends LitePalSupport {
    String username;
    String password;


    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
