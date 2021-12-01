package br.com.obpcbooks.dto;

import java.io.Serializable;

public class LivroDTO implements Serializable {

    private String id;
    private String title;
    private String author;
    private String summary;
    private Integer year;
    private String image;
    private String publisher;
    private Integer availables;

    public LivroDTO() {   }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getAvailables() {
        return availables;
    }

    public void setAvailables(Integer availables) {
        this.availables = availables;
    }
}
