package com.SCM.SCM20.Helper;


public class Message {

    private String content;
    private MessageType type=MessageType.blue;



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Message() {
    }

    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }
}
