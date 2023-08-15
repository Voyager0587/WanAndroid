package com.example.wanandroid.bean;

import java.util.List;

/**
 * @version 1.0
 * @className: WXArticleBean
 * @author: Voyager
 * @description: TODO
 * @date: 2023/8/14 20:18
 **/
public class WXArticleBean {


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
         * curPage : 1
         * datas : [{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26992,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/Q2X63vYSi4IkUi5cfwppqw","niceDate":"2023-08-10 00:00","niceShareDate":"2023-08-10 22:52","origin":"","prefix":"","projectLink":"","publishTime":1691596800000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1691679121000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"使用 AndroidX 增强 WebView 的能力","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26991,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/4DOy3O4v39afC-TJEPYAiw","niceDate":"2023-08-09 00:00","niceShareDate":"2023-08-10 22:51","origin":"","prefix":"","projectLink":"","publishTime":1691510400000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1691679106000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android Kernel内核和AOSP代码怎么轻松查看？","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26984,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/-odSeh1I78H1kDsm5MHkag","niceDate":"2023-08-08 00:00","niceShareDate":"2023-08-09 09:11","origin":"","prefix":"","projectLink":"","publishTime":1691424000000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1691543518000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android Studio 实用插件推荐","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26977,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/fMz1AfeYGigclZXuFgmrrg","niceDate":"2023-08-07 00:00","niceShareDate":"2023-08-08 09:28","origin":"","prefix":"","projectLink":"","publishTime":1691337600000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1691458080000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"一文打造Android热修复专家","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26978,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/ZFXb46bJcIL0q2gOWYl-Jw","niceDate":"2023-08-04 00:00","niceShareDate":"2023-08-08 09:28","origin":"","prefix":"","projectLink":"","publishTime":1691078400000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1691458092000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android 性能优化，网络预连接","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26964,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/BpwZsdGoIbhyRCDz6GD_tg","niceDate":"2023-08-03 00:00","niceShareDate":"2023-08-04 09:14","origin":"","prefix":"","projectLink":"","publishTime":1690992000000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1691111670000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"匿名内部类/Lambda Java和Kotlin谁会导致内存泄漏?","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26960,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/dlVO7MXoyVr7UHQ_kCwwfw","niceDate":"2023-08-02 00:00","niceShareDate":"2023-08-03 09:17","origin":"","prefix":"","projectLink":"","publishTime":1690905600000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1691025437000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"前段时间面试了一些人，有这些槽点跟大家说说\u200b","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26945,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/ViogsocH9yusAci4NHxCiQ","niceDate":"2023-08-01 00:00","niceShareDate":"2023-08-01 22:45","origin":"","prefix":"","projectLink":"","publishTime":1690819200000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1690901130000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android WebView H5 秒开方案总结","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26944,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/fJHLBXZiqHrCb-llHmcWfA","niceDate":"2023-07-28 00:00","niceShareDate":"2023-08-01 22:45","origin":"","prefix":"","projectLink":"","publishTime":1690473600000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1690901111000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Compose 为什么可以跨平台？","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26922,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/1UfqiJgG7DvWR4u5TMShAA","niceDate":"2023-07-27 00:00","niceShareDate":"2023-07-28 09:15","origin":"","prefix":"","projectLink":"","publishTime":1690387200000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1690506929000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"推荐几个开源项目，也许对你有所帮助","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26918,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/KXnfy8krwnIbp3vC-4NvRg","niceDate":"2023-07-26 00:00","niceShareDate":"2023-07-27 09:19","origin":"","prefix":"","projectLink":"","publishTime":1690300800000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1690420742000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"神器的Binder，如何做到跨进程权限控制的？","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26915,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/IVRiDcIa75Yn4VmmKghlKA","niceDate":"2023-07-25 00:00","niceShareDate":"2023-07-26 09:17","origin":"","prefix":"","projectLink":"","publishTime":1690214400000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1690334247000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android录制视频，仿微信老版录制页面","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26905,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/pINFm_qBYO0MQC4Jr1EnTA","niceDate":"2023-07-21 00:00","niceShareDate":"2023-07-23 22:27","origin":"","prefix":"","projectLink":"","publishTime":1689868800000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1690122461000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Gradle 构建，又冲突了？","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26881,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/Ud_ozg5SbJoA75Yc4pS8Lw","niceDate":"2023-07-19 00:00","niceShareDate":"2023-07-19 22:11","origin":"","prefix":"","projectLink":"","publishTime":1689696000000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1689775913000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"号称零反射的Shadow插件化框架解析","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26880,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/vw2Qivkn7vv_efU0ta1MwQ","niceDate":"2023-07-18 00:00","niceShareDate":"2023-07-19 22:11","origin":"","prefix":"","projectLink":"","publishTime":1689609600000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1689775897000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android录制视频，三种Camera API的使用与预览","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26867,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/wr0ckyQOELAd9zD3ymssNw","niceDate":"2023-07-17 00:00","niceShareDate":"2023-07-18 09:12","origin":"","prefix":"","projectLink":"","publishTime":1689523200000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1689642721000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"聊聊Compose跨平台与KMM","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26866,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/IxNhn0wlsPHXuJ3_n11vYQ","niceDate":"2023-07-14 00:00","niceShareDate":"2023-07-18 09:11","origin":"","prefix":"","projectLink":"","publishTime":1689264000000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1689642704000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"深度解读 Android 14 的 8 个重要新特性～","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26848,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/ulrFSuwutq_UOtXhPoAD8A","niceDate":"2023-07-13 00:00","niceShareDate":"2023-07-13 23:01","origin":"","prefix":"","projectLink":"","publishTime":1689177600000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1689260517000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"OutOfMemoryError是如何产生的？有这么多情况？","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26847,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/3a2FfPKGGHROymH4dOVD_g","niceDate":"2023-07-12 00:00","niceShareDate":"2023-07-13 23:01","origin":"","prefix":"","projectLink":"","publishTime":1689091200000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1689260494000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"字节跳动开源神器：btrace 2.0 技术原理大揭秘","type":0,"userId":-1,"visible":1,"zan":0},{"adminAdd":false,"apkLink":"","audit":1,"author":"鸿洋","canEdit":false,"chapterId":408,"chapterName":"鸿洋","collect":false,"courseId":13,"desc":"","descMd":"","envelopePic":"","fresh":false,"host":"","id":26835,"isAdminAdd":false,"link":"https://mp.weixin.qq.com/s/RnlSXZjVXjAcy_BtqSz-jw","niceDate":"2023-07-11 00:00","niceShareDate":"2023-07-11 23:57","origin":"","prefix":"","projectLink":"","publishTime":1689004800000,"realSuperChapterId":407,"selfVisible":0,"shareDate":1689091031000,"shareUser":"","superChapterId":408,"superChapterName":"公众号","tags":[{"name":"公众号","url":"/wxarticle/list/408/1"}],"title":"Android 冷启优化的3个黑科技","type":0,"userId":-1,"visible":1,"zan":0}]
         * offset : 0
         * over : false
         * pageCount : 85
         * size : 20
         * total : 1682
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * adminAdd : false
             * apkLink :
             * audit : 1
             * author : 鸿洋
             * canEdit : false
             * chapterId : 408
             * chapterName : 鸿洋
             * collect : false
             * courseId : 13
             * desc :
             * descMd :
             * envelopePic :
             * fresh : false
             * host :
             * id : 26992
             * isAdminAdd : false
             * link : https://mp.weixin.qq.com/s/Q2X63vYSi4IkUi5cfwppqw
             * niceDate : 2023-08-10 00:00
             * niceShareDate : 2023-08-10 22:52
             * origin :
             * prefix :
             * projectLink :
             * publishTime : 1691596800000
             * realSuperChapterId : 407
             * selfVisible : 0
             * shareDate : 1691679121000
             * shareUser :
             * superChapterId : 408
             * superChapterName : 公众号
             * tags : [{"name":"公众号","url":"/wxarticle/list/408/1"}]
             * title : 使用 AndroidX 增强 WebView 的能力
             * type : 0
             * userId : -1
             * visible : 1
             * zan : 0
             */

            private boolean adminAdd;
            private String apkLink;
            private int audit;
            private String author;
            private boolean canEdit;
            private int chapterId;
            private String chapterName;
            private boolean collect;
            private int courseId;
            private String desc;
            private String descMd;
            private String envelopePic;
            private boolean fresh;
            private String host;
            private int id;
            private boolean isAdminAdd;
            private String link;
            private String niceDate;
            private String niceShareDate;
            private String origin;
            private String prefix;
            private String projectLink;
            private long publishTime;
            private int realSuperChapterId;
            private int selfVisible;
            private long shareDate;
            private String shareUser;
            private int superChapterId;
            private String superChapterName;
            private String title;
            private int type;
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
                 * name : 公众号
                 * url : /wxarticle/list/408/1
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
}
