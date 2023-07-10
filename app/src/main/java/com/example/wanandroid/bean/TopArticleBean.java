package com.example.wanandroid.bean;

import java.util.List;

/**
 * @className: TopArticleBean
 * @author: Voyager
 * @description: 置顶文章的数据类
 * @date: 2023/6/25
 **/
public class TopArticleBean {

    /**
     * data : [{"adminAdd":false,"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>更近遇到一类反馈，更终排查定位到是存储在应用私有cache目录（data/data/包名/cache）的一些文件被删除了（这里是文件夹内，部分文件被删除）；<\/p>\r\n<p>问题来了：<\/p>\r\n<p>系统对于cache目录的清理策略是怎么样的？在高版本上有什么策略调整吗？<\/p>","descMd":"","envelopePic":"","fresh":false,"host":"","id":26673,"isAdminAdd":false,"link":"https://wanandroid.com/wenda/show/26673","niceDate":"2023-06-14 11:22","niceShareDate":"2023-06-14 11:22","origin":"","prefix":"","projectLink":"","publishTime":1686712973000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1686712956000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | 系统会随意删除App的缓存文件？","type":1,"userId":2,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>在framework的代码中，经常看到如下的权限检测的代码：<\/p>\r\n<p><img src=\"https://wanandroid.com/blogimgs/af042353-c7c6-4f29-a988-3ad9b369964d.png\" alt=\"q1.png\" /><\/p>\r\n<p><img src=\"https://wanandroid.com/blogimgs/01fdb9cf-6f44-48bf-aa48-0cd527bfebd0.png\" alt=\"q2.png\" /><\/p>\r\n<p>Binder.getCallingUid()字面理解是获取调用方的uid，但是这个代码是目标进程调用的，如何通过一个静态方法调用，就拿到调用方的uid呢？<\/p>","descMd":"","envelopePic":"","fresh":false,"host":"","id":26624,"isAdminAdd":false,"link":"https://wanandroid.com/wenda/show/26624","niceDate":"2023-06-07 21:01","niceShareDate":"2023-06-07 21:01","origin":"","prefix":"","projectLink":"","publishTime":1686142904000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1686142876000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Binder是如何做到跨进程权限控制的？","type":1,"userId":2,"visible":1,"zan":1},{"adminAdd":false,"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<pre><code>package org.example;\r\n\r\npublic class ParentJava {\r\n    public String name;\r\n}\r\n\r\n import org.example.ParentJava\r\n\r\nclass Child(val name: String): ParentJava()\r\n\r\nfun main() {\r\n    Child(&quot;&quot;).name\r\n}\r\n<\/code><\/pre><p>如上代码，运行闪退。<\/p>\r\n<p>问：为什么？<\/p>\r\n<p>问题来源于<a href=\"https://www.wanandroid.com/wenda/show/8857?fid=225&amp;date=2023_05_31_17_12_04&amp;message=package%20or#msg_id2773\">xujiafeng<\/a><\/p>","descMd":"","envelopePic":"","fresh":false,"host":"","id":26578,"isAdminAdd":false,"link":"https://www.wanandroid.com/wenda/show/26578","niceDate":"2023-05-31 21:20","niceShareDate":"2023-05-31 21:19","origin":"","prefix":"","projectLink":"","publishTime":1685539214000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1685539198000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问 | Java 系列，奇怪的闪退？","type":1,"userId":2,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"xiaoyang","canEdit":false,"chapterId":440,"chapterName":"官方","collect":false,"courseId":13,"desc":"<p>当我们递归调用Java方法时，很可能会出现StackOverflowError，我们会认为此时栈内存溢出了，那么这个栈内存溢出虚拟机是如何检测的呢？<\/p>\r\n<p>是累加分配的内存与栈大小进行比较，还是有更好的方式呢？<\/p>","descMd":"","envelopePic":"","fresh":false,"host":"","id":26503,"isAdminAdd":false,"link":"https://wanandroid.com/wenda/show/26503","niceDate":"2023-05-24 17:35","niceShareDate":"2023-05-24 17:30","origin":"","prefix":"","projectLink":"","publishTime":1684920924000,"realSuperChapterId":439,"selfVisible":0,"shareDate":1684920617000,"shareUser":"","superChapterId":440,"superChapterName":"问答","tags":[{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}],"title":"每日一问  | Java线程栈的栈溢出（StackOverflowError）是如何检测的？","type":1,"userId":2,"visible":1,"zan":3}]
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
         * adminAdd : false
         * apkLink :
         * audit : 1
         * author : xiaoyang
         * canEdit : false
         * chapterId : 440
         * chapterName : 官方
         * collect : false
         * courseId : 13
         * desc : <p>更近遇到一类反馈，更终排查定位到是存储在应用私有cache目录（data/data/包名/cache）的一些文件被删除了（这里是文件夹内，部分文件被删除）；</p>
         <p>问题来了：</p>
         <p>系统对于cache目录的清理策略是怎么样的？在高版本上有什么策略调整吗？</p>
         * descMd :
         * envelopePic :
         * fresh : false
         * host :
         * id : 26673
         * isAdminAdd : false
         * link : https://wanandroid.com/wenda/show/26673
         * niceDate : 2023-06-14 11:22
         * niceShareDate : 2023-06-14 11:22
         * origin :
         * prefix :
         * projectLink :
         * publishTime : 1686712973000
         * realSuperChapterId : 439
         * selfVisible : 0
         * shareDate : 1686712956000
         * shareUser :
         * superChapterId : 440
         * superChapterName : 问答
         * tags : [{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}]
         * title : 每日一问 | 系统会随意删除App的缓存文件？
         * type : 1
         * userId : 2
         * visible : 1
         * zan : 0
         */

        private String author;
        private String chapterName;
        private String link;
        private String niceDate;
        private String title;
        private int type;
        private String shareUser;


        private boolean adminAdd;
        private String apkLink;
        private int audit;
        private boolean canEdit;
        private int chapterId;
        private boolean collect;
        private int courseId;
        private String desc;
        private String descMd;
        private String envelopePic;
        private boolean fresh;
        private String host;
        private int id;
        private boolean isAdminAdd;


        private String niceShareDate;
        private String origin;
        private String prefix;
        private String projectLink;
        private long publishTime;
        private int realSuperChapterId;
        private int selfVisible;
        private long shareDate;
        private int superChapterId;
        private String superChapterName;
        private int userId;
        private int visible;
        private int zan;
        private List<TagsBean> tags;

        public boolean isAdminAdd() {
            return adminAdd;
        }

        public void setAdminAdd(boolean adminAdd) {
            this.adminAdd = adminAdd;
        }

        public String getApkLink() {
            return apkLink;
        }

        public void setApkLink(String apkLink) {
            this.apkLink = apkLink;
        }

        public int getAudit() {
            return audit;
        }

        public void setAudit(int audit) {
            this.audit = audit;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public boolean isCanEdit() {
            return canEdit;
        }

        public void setCanEdit(boolean canEdit) {
            this.canEdit = canEdit;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDescMd() {
            return descMd;
        }

        public void setDescMd(String descMd) {
            this.descMd = descMd;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public boolean isFresh() {
            return fresh;
        }

        public void setFresh(boolean fresh) {
            this.fresh = fresh;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsAdminAdd() {
            return isAdminAdd;
        }

        public void setIsAdminAdd(boolean isAdminAdd) {
            this.isAdminAdd = isAdminAdd;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getNiceShareDate() {
            return niceShareDate;
        }

        public void setNiceShareDate(String niceShareDate) {
            this.niceShareDate = niceShareDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getProjectLink() {
            return projectLink;
        }

        public void setProjectLink(String projectLink) {
            this.projectLink = projectLink;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public int getRealSuperChapterId() {
            return realSuperChapterId;
        }

        public void setRealSuperChapterId(int realSuperChapterId) {
            this.realSuperChapterId = realSuperChapterId;
        }

        public int getSelfVisible() {
            return selfVisible;
        }

        public void setSelfVisible(int selfVisible) {
            this.selfVisible = selfVisible;
        }

        public long getShareDate() {
            return shareDate;
        }

        public void setShareDate(long shareDate) {
            this.shareDate = shareDate;
        }

        public String getShareUser() {
            return shareUser;
        }

        public void setShareUser(String shareUser) {
            this.shareUser = shareUser;
        }

        public int getSuperChapterId() {
            return superChapterId;
        }

        public void setSuperChapterId(int superChapterId) {
            this.superChapterId = superChapterId;
        }

        public String getSuperChapterName() {
            return superChapterName;
        }

        public void setSuperChapterName(String superChapterName) {
            this.superChapterName = superChapterName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * name : 本站发布
             * url : /article/list/0?cid=440
             */

            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
