package persistence01;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

//@Entity
@Table(name = "BOOK_HISTORY")
public class BookHistory implements Serializable{
	
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;
    
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
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) throws ParseException {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) throws ParseException {
		this.end = end;
	}
	
	public BookHistory() {}	

	public BookHistory(String title, Float price, String description, String isbn, Integer nbOfPage,
			Boolean illustrations) {
		super();
		this.title = title;
		this.price = price;
		this.description = description;
		this.isbn = isbn;
		this.nbOfPage = nbOfPage;
		this.illustrations = illustrations;
	}
	public BookHistory(String title, Float price, String description, String isbn, Integer nbOfPage,
			Boolean illustrations, Date start, Date end) {
		super();
		this.title = title;
		this.price = price;
		this.description = description;
		this.isbn = isbn;
		this.nbOfPage = nbOfPage;
		this.illustrations = illustrations;
		this.start = start;
		this.end = end;
	}
	
	

}
