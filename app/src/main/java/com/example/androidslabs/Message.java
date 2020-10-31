package com.example.androidslabs;

public class Message {

    private long id;
    private String content;
    private boolean isSent;

    public Message(long id, String content, boolean isSent) {
        this.id = id;
        this.content = content;
        this.isSent = isSent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

}
