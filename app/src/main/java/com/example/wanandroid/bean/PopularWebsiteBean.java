package com.example.wanandroid.bean;

import java.util.List;

/**
 * @className: PopularWebsiteBean
 * @author: Voyager
 * @description: 常用网站的数据类
 * @date: 2023/6/26
 **/
public class PopularWebsiteBean {

    /**
     * data : [{"category":"源码","icon":"","id":22,"link":"https://www.androidos.net.cn/sourcecode","name":"androidos","order":11,"visible":1},{"category":"设计","icon":"","id":31,"link":"https://tool.gifhome.com/compress/","name":"gif压缩","order":4444,"visible":1}]
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * category : 源码
         * icon :
         * id : 22
         * link : https://www.androidos.net.cn/sourcecode
         * name : androidos
         * order : 11
         * visible : 1
         */

        private String category;
        private String icon;
        private int id;
        private String link;
        private String name;
        private int order;
        private int visible;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }
    }
}
