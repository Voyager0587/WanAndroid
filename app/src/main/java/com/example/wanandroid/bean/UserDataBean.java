package com.example.wanandroid.bean;

import java.util.List;

/**
 * @version 1.0
 * @className: UserDataBean
 * @author: Voyager
 * @description: 用户信息数据类
 * @date: 2023/8/6 12:43
 **/
public class UserDataBean {

    /**
     * data : {"coinInfo":{"coinCount":36662,"level":367,"nickname":"","rank":"3","userId":2,"username":"x**oyang"},"userInfo":{"admin":false,"chapterTops":[],"coinCount":36662,"collectIds":[],"email":"623565791@qq.com","icon":"","id":2,"nickname":"鸿洋","password":"","publicName":"鸿洋","token":"","type":0,"username":"xiaoyang"}}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * coinInfo : {"coinCount":36662,"level":367,"nickname":"","rank":"3","userId":2,"username":"x**oyang"}
         * userInfo : {"admin":false,"chapterTops":[],"coinCount":36662,"collectIds":[],"email":"623565791@qq.com","icon":"","id":2,"nickname":"鸿洋","password":"","publicName":"鸿洋","token":"","type":0,"username":"xiaoyang"}
         */

        private CoinInfoBean coinInfo;
        private UserInfoBean userInfo;

        public CoinInfoBean getCoinInfo() {
            return coinInfo;
        }

        public void setCoinInfo(CoinInfoBean coinInfo) {
            this.coinInfo = coinInfo;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class CoinInfoBean {
            /**
             * coinCount : 36662
             * level : 367
             * nickname :
             * rank : 3
             * userId : 2
             * username : x**oyang
             */

            /**
             * 积分
             */
            private int coinCount;

            /**
             * 排名
             */
            private int level;

            private String nickname;
            private String rank;
            private int userId;
            private String username;

            public int getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(int coinCount) {
                this.coinCount = coinCount;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }

        public static class UserInfoBean {
            /**
             * admin : false
             * chapterTops : []
             * coinCount : 36662
             * collectIds : []
             * email : 623565791@qq.com
             * icon :
             * id : 2
             * nickname : 鸿洋
             * password :
             * publicName : 鸿洋
             * token :
             * type : 0
             * username : xiaoyang
             */

            /**
             * 用户名
             */
            private String publicName;

            /**
             * 邮箱
             */
            private String email;

            private boolean admin;
            private int coinCount;
            private String icon;
            private int id;
            private String nickname;
            private String password;
            private String token;
            private int type;
            private String username;
            private List<?> chapterTops;
            private List<?> collectIds;

            public boolean isAdmin() {
                return admin;
            }

            public void setAdmin(boolean admin) {
                this.admin = admin;
            }

            public int getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(int coinCount) {
                this.coinCount = coinCount;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPublicName() {
                return publicName;
            }

            public void setPublicName(String publicName) {
                this.publicName = publicName;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public List<?> getChapterTops() {
                return chapterTops;
            }

            public void setChapterTops(List<?> chapterTops) {
                this.chapterTops = chapterTops;
            }

            public List<?> getCollectIds() {
                return collectIds;
            }

            public void setCollectIds(List<?> collectIds) {
                this.collectIds = collectIds;
            }
        }
    }
}
