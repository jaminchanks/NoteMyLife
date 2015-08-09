package com.challenger.jamin.notemylife2.bean;

/**
 * Created by jamin on 7/29/15.
 */
public class Book {
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    private int noteId;
    private String noteName;
}
