package br.com.obpcbooks.dto;

import android.text.format.DateFormat;

import java.util.Date;

public class ComentarioDTO {

    private String id;

    private String dateCreation;

    private String text;

    private String bookId;

    private UserDTO user;

    public ComentarioDTO() {
        this.dateCreation =  DateFormat.format("dd-MM-yyyy HH:mm:sss.sss", new Date()).toString();
    }

    public ComentarioDTO(String id, String dateCreation, String text, String bookId, UserDTO user) {
        this.id = id;
        this.dateCreation =  DateFormat.format("dd-MM-yyyy HH:mm:sss.sss", new Date()).toString();
        this.text = text;
        this.bookId = bookId;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
