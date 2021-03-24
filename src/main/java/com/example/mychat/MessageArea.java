package com.example.mychat;
import java.util.Date;
public class MessageArea {
    private String messageText;
    private String messageUser;
    private long messageTime;
    public MessageArea(String messageText, String messageUser){
        this.messageText = messageText;
        this.messageUser = messageUser;
        messageTime = new Date().getTime();
    }

    public long getMessageTime() {
        return messageTime;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
