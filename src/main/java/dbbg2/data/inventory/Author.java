package dbbg2.data.inventory;

import org.eclipse.persistence.annotations.Index;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Index(name="AUTHOR_NAME_INDEX", columnNames = {"L_NAME", "F_NAME"})
public class Author {

    @Column(name="F_NAME")
    private String firstName;
    @Column(name="L_NAME")
    private String lastName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "Book_author",
            joinColumns = {@JoinColumn(name = "Author_name")},
            inverseJoinColumns = {@JoinColumn(name = "Inventory_Id")})
    private Set<Book> books = new HashSet<>();

    public Author(){

    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void removeBook(Book book){
        books.remove(book);
    }
    public void addBook(Book book){
        books.add(book);
    }


}
