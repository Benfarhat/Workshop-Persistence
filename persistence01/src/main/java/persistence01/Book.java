package persistence01;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheCoordinationType;
import org.eclipse.persistence.annotations.CacheType;

@Entity
@NamedQuery(name = "findAllBooks",
            query = "SELECT b from Book b")
@Cache(
  type=CacheType.SOFT, // Cache everything until the JVM decides memory is low.
  size=64000,  // Use 64,000 as the initial cache size.
  expiry=360000,  // 6 minutes
  coordinationType=CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS  // if cache coordination is used, only send invalidation messages.
)
//@Customizer(persistence01.HistoryCustomizer.class)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    private Float price;
    @Column(length = 2000)
    private String description;
    private String isbn;
    private Integer nbOfPage;
    private Boolean illustrations;
    
    // Constructors
    public Book() {}
    
    public Book(String title, Float price, String description, String isbn, Integer nbOfPage, Boolean illustrations) {
        super();
        this.title = title;
        this.price = price;
        this.description = description;
        this.isbn = isbn;
        this.nbOfPage = nbOfPage;
        this.illustrations = illustrations;
    }


    // Getters and Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getNbOfPage() {
        return nbOfPage;
    }

    public void setNbOfPage(Integer nbOfPage) {
        this.nbOfPage = nbOfPage;
    }

    public Boolean getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(Boolean illustrations) {
        this.illustrations = illustrations;
    };
    
    

}
