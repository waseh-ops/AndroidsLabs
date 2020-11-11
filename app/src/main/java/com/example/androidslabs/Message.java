package com.example.androidslabs;

public class Message {

     long id;
     String content;
     boolean isSent;

    public Message(){}

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

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", isSent=" + isSent +
                '}';
    }
}
