package br.com.obpcbooks.dto;

import android.text.format.DateFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.obpcbooks.enums.ReservaStatus;
import br.com.obpcbooks.model.User;

public class ReservaDTO implements Serializable {

    private String id;
    private String dateCreation;
    private String pickupDate;
    private String previewDevolutionDate;
    private String devolutionDate;
    private String status;
    private String userId;
    private String userName;
    private List<LivroDTO> books; // usado para receber BookingRepresentation
    private List<String> booksId; // usado para representar BookingDTO


    public ReservaDTO() {}

    public ReservaDTO(User user, List<LivroDTO> listaLivros){
        this.userId = user.getId();
        this.userName = user.getUsername();
        this.books = listaLivros;
        popularListaDeLivrosID();

    }

    public void popularListaDeLivrosID(){
        this.booksId = new ArrayList<>();
        for (LivroDTO livro : this.books) {
            booksId.add(livro.getId());
        }
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

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPreviewDevolutionDate() {
        return previewDevolutionDate;
    }

    public void setPreviewDevolutionDate(String previewDevolutionDate) {
        this.previewDevolutionDate = previewDevolutionDate;
    }

    public String getDevolutionDate() {
        return devolutionDate;
    }

    public void setDevolutionDate(String devolutionDate) {
        this.devolutionDate = devolutionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<LivroDTO> getBooks() {
        return books;
    }

    public void setBooks(List<LivroDTO> books) {
        this.books = books;
    }

    public List<String> getBooksId() {
        return booksId;
    }

    public void setBooksId(List<String> booksId) {
        this.booksId = booksId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
