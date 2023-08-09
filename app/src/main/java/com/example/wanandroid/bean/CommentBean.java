package com.example.wanandroid.bean;

import java.util.List;

/**
 * @version 1.0
 * @className: CommentBean
 * @author: Voyager
 * @description: 消息数据类
 * @date: 2023/8/9 0:59
 **/
public class CommentBean {

    /**
     * data : {"curPage":1,"datas":[{"category":1,"date":1691049807000,"fromUser":"136013903@qq.com","fromUserId":71433,"fullLink":"https://wanandroid.com/wenda/show/26673","id":761336,"isRead":1,"link":"/wenda/show/26673","message":"海信的狗系统也是,,.,用着用着,...总是提示内存不足,还老是重启...最后刷机好了...","niceDate":"2023-08-03 16:03","tag":"评论回复","title":"回复了@xujiafeng","userId":150362},{"category":1,"date":1688393945000,"fromUser":"626821430","fromUserId":886,"fullLink":"https://wanandroid.com/wenda/show/26503","id":751947,"isRead":1,"link":"/wenda/show/26503","message":"这里的8192是一个经验值，一般情况下，栈的大小为8KB，所以这里减去8192是为了获取栈底的地址。如果当前函数的栈帧地址小于栈底的地址，就说明栈已经溢出了，需要抛出StackOverflowError","niceDate":"2023-07-03 22:19","tag":"评论回复","title":"回复了@残页","userId":150362},{"category":1,"date":1687935489000,"fromUser":"残页","fromUserId":12467,"fullLink":"https://wanandroid.com/wenda/show/26503","id":749522,"isRead":1,"link":"/wenda/show/26503","message":"解释执行是手动 if 来检测的，可以参考第一段列出来链接，点进去看代码","niceDate":"2023-06-28 14:58","tag":"评论回复","title":"回复了@ZCJ风飞","userId":150362}],"offset":0,"over":true,"pageCount":1,"size":20,"total":3}
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
         * curPage : 1
         * datas : [{"category":1,"date":1691049807000,"fromUser":"136013903@qq.com","fromUserId":71433,"fullLink":"https://wanandroid.com/wenda/show/26673","id":761336,"isRead":1,"link":"/wenda/show/26673","message":"海信的狗系统也是,,.,用着用着,...总是提示内存不足,还老是重启...最后刷机好了...","niceDate":"2023-08-03 16:03","tag":"评论回复","title":"回复了@xujiafeng","userId":150362},{"category":1,"date":1688393945000,"fromUser":"626821430","fromUserId":886,"fullLink":"https://wanandroid.com/wenda/show/26503","id":751947,"isRead":1,"link":"/wenda/show/26503","message":"这里的8192是一个经验值，一般情况下，栈的大小为8KB，所以这里减去8192是为了获取栈底的地址。如果当前函数的栈帧地址小于栈底的地址，就说明栈已经溢出了，需要抛出StackOverflowError","niceDate":"2023-07-03 22:19","tag":"评论回复","title":"回复了@残页","userId":150362},{"category":1,"date":1687935489000,"fromUser":"残页","fromUserId":12467,"fullLink":"https://wanandroid.com/wenda/show/26503","id":749522,"isRead":1,"link":"/wenda/show/26503","message":"解释执行是手动 if 来检测的，可以参考第一段列出来链接，点进去看代码","niceDate":"2023-06-28 14:58","tag":"评论回复","title":"回复了@ZCJ风飞","userId":150362}]
         * offset : 0
         * over : true
         * pageCount : 1
         * size : 20
         * total : 3
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
             * category : 1
             * date : 1691049807000
             * fromUser : 136013903@qq.com
             * fromUserId : 71433
             * fullLink : https://wanandroid.com/wenda/show/26673
             * id : 761336
             * isRead : 1
             * link : /wenda/show/26673
             * message : 海信的狗系统也是,,.,用着用着,...总是提示内存不足,还老是重启...最后刷机好了...
             * niceDate : 2023-08-03 16:03
             * tag : 评论回复
             * title : 回复了@xujiafeng
             * userId : 150362
             */

            private int category;
            private long date;
            private String fromUser;
            private int fromUserId;
            private String fullLink;
            private int id;
            private int isRead;
            private String link;
            private String message;
            private String niceDate;
            private String tag;
            private String title;
            private int userId;

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public long getDate() {
                return date;
            }

            public void setDate(long date) {
                this.date = date;
            }

            public String getFromUser() {
                return fromUser;
            }

            public void setFromUser(String fromUser) {
                this.fromUser = fromUser;
            }

            public int getFromUserId() {
                return fromUserId;
            }

            public void setFromUserId(int fromUserId) {
                this.fromUserId = fromUserId;
            }

            public String getFullLink() {
                return fullLink;
            }

            public void setFullLink(String fullLink) {
                this.fullLink = fullLink;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
