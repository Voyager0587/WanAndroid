package com.example.wanandroid.bean;

import java.util.List;

/**
 * @className: ChapterBean
 * @author: Voyager
 * @description: 文章体系的数据类，储存一级和二级标题
 * @date: 2023/7/4
 **/
public class ChapterBean {

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
         * articleList : []
         * author :
         * children : [{"articleList":[],"author":"","children":[],"courseId":13,"cover":"","desc":"","id":60,"lisense":"","lisenseLink":"","name":"Android Studio相关","order":1000,"parentChapterId":150,"type":0,"userControlSetTop":false,"visible":1},{"articleList":[],"author":"","children":[],"courseId":13,"cover":"","desc":"","id":169,"lisense":"","lisenseLink":"","name":"gradle","order":1001,"parentChapterId":150,"type":0,"userControlSetTop":false,"visible":1},{"articleList":[],"author":"","children":[],"courseId":13,"cover":"","desc":"","id":269,"lisense":"","lisenseLink":"","name":"官方发布","order":1002,"parentChapterId":150,"type":0,"userControlSetTop":false,"visible":1},{"articleList":[],"author":"","children":[],"courseId":13,"cover":"","desc":"","id":529,"lisense":"","lisenseLink":"","name":"90-120hz","order":1003,"parentChapterId":150,"type":0,"userControlSetTop":false,"visible":1}]
         * courseId : 13
         * cover :
         * desc :
         * id : 150
         * lisense :
         * lisenseLink :
         * name : 开发环境
         * order : 1
         * parentChapterId : 0
         * type : 0
         * userControlSetTop : false
         * visible : 1
         */


        /**
         * 一级分类·标题
         */
        private String name;
        /**
         * 一级分类id
         */
        private int id;
        private String author;
        private int courseId;
        private String cover;
        private String desc;

        private String lisense;
        private String lisenseLink;

        private int order;
        private int parentChapterId;
        private int type;
        private boolean userControlSetTop;
        private int visible;
        private List<?> articleList;
        private List<ChildrenBean> children;

        public DataBean(String name) {
            this.name = name;
        }

        public DataBean() {
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLisense() {
            return lisense;
        }

        public void setLisense(String lisense) {
            this.lisense = lisense;
        }

        public String getLisenseLink() {
            return lisenseLink;
        }

        public void setLisenseLink(String lisenseLink) {
            this.lisenseLink = lisenseLink;
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

        public int getParentChapterId() {
            return parentChapterId;
        }

        public void setParentChapterId(int parentChapterId) {
            this.parentChapterId = parentChapterId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isUserControlSetTop() {
            return userControlSetTop;
        }

        public void setUserControlSetTop(boolean userControlSetTop) {
            this.userControlSetTop = userControlSetTop;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public List<?> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<?> articleList) {
            this.articleList = articleList;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * articleList : []
             * author :
             * children : []
             * courseId : 13
             * cover :
             * desc :
             * id : 60
             * lisense :
             * lisenseLink :
             * name : Android Studio相关
             * order : 1000
             * parentChapterId : 150
             * type : 0
             * userControlSetTop : false
             * visible : 1
             */

            /**
             * 二级分类标题
             */
            private String name;

            /**
             * 二级分类ID
             */
            private int id;
            private String author;
            private int courseId;
            private String cover;
            private String desc;
            private String lisense;
            private String lisenseLink;

            private int order;
            private int parentChapterId;
            private int type;
            private boolean userControlSetTop;
            private int visible;
            private List<?> articleList;
            private List<?> children;

            public ChildrenBean() {
            }

            public ChildrenBean(String name) {
                this.name = name;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLisense() {
                return lisense;
            }

            public void setLisense(String lisense) {
                this.lisense = lisense;
            }

            public String getLisenseLink() {
                return lisenseLink;
            }

            public void setLisenseLink(String lisenseLink) {
                this.lisenseLink = lisenseLink;
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

            public int getParentChapterId() {
                return parentChapterId;
            }

            public void setParentChapterId(int parentChapterId) {
                this.parentChapterId = parentChapterId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public boolean isUserControlSetTop() {
                return userControlSetTop;
            }

            public void setUserControlSetTop(boolean userControlSetTop) {
                this.userControlSetTop = userControlSetTop;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public List<?> getArticleList() {
                return articleList;
            }

            public void setArticleList(List<?> articleList) {
                this.articleList = articleList;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }
        }
    }
}
