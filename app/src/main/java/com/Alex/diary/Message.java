package com.Alex.diary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {

    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("tid")
    @Expose
    private Integer tid;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public class Datum {

        @SerializedName("FROM")
        @Expose
        private String from;
        @SerializedName("TO")
        @Expose
        private String to;
        @SerializedName("DATE")
        @Expose
        private Long date;
        @SerializedName("TEXT")
        @Expose
        private String text;
        @SerializedName("UNREAD")
        @Expose
        private Boolean unread;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public Long getDate() {
            return date;
        }

        public void setDate(Long date) {
            this.date = date;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Boolean getUnread() {
            return unread;
        }

        public void setUnread(Boolean unread) {
            this.unread = unread;
        }

    }
}
