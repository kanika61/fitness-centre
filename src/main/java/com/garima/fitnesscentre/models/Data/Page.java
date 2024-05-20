package com.garima.fitnesscentre.models.Data;





import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="pages")
public class Page 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
   
   @Size(min=2, message="Title must be atleast two character")
    private String title;
    
    private String slug;
   @Size(min=5, message="Title must be atleast two character")
    private String content;

    private int sorting;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSorting() {
        return sorting;
    }

    public void setSorting(int sorting) {
        this.sorting = sorting;
    }
    
    
}
