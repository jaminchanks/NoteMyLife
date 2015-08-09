package com.challenger.jamin.notemylife2.bean;

/**
 * Created by jamin on 8/8/15.
 */
public class ChatMessage {
    public static final int TYPE_RECEIVE = 0;
    public static final int TYPE_SEND = 1;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String content;
    private int type;

    public ChatMessage(String content, int type) {
        this.content = content;
        this.type = type;
    }


}
