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
import java.util.Objects;


public class Author {

    public static final int VERSION = 79897;
    private final int author_id;
    private String author;
    private String notes;

    public Author(int author_id, String author) {
        this(author_id, author, null);
    }

    public Author(int author_id, String author, String notes) {
        this.author_id = author_id;
        this.author = author;
        this.notes = notes;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        return VERSION + this.author_id + Objects.hashCode(this.author);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Author)) {
            return false;
        }

        final Author other = (Author) obj;
        return !(this.author_id != other.author_id
                || !Objects.equals(this.author, other.author));
    }

}
