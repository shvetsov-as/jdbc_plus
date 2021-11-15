/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author User
 */
import java.time.LocalDate;
//import java.util.Date;
import java.util.Objects;


public class Document {

    public static final int VERSION = 267384;
    private final int document_id;
    private String title;
    private String text;
    private LocalDate date;
    private int author_id;

    public Document(int document_id, String title, String text, int author_id) {
        this(document_id, title, text,
                LocalDate.now(), author_id);
    }

    public Document(int document_id, String title, String text, LocalDate date, int author_id) {
        this.document_id = document_id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.author_id = author_id;
    }

    public int getDocument_id() {
        return document_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    @Override
    public int hashCode() {
        return VERSION + this.document_id + Objects.hashCode(this.title) + this.author_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Document other = (Document) obj;
        return !(this.document_id != other.document_id
                || this.author_id != other.author_id
                || !Objects.equals(this.title, other.title));
    }

}
