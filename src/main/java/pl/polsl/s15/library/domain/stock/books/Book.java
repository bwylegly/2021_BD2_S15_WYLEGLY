package pl.polsl.s15.library.domain.stock.books;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.s15.library.controller.StockController;
import pl.polsl.s15.library.domain.stock.StockItem;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book extends StockItem {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id")
    private BookDetails details;
    public Book(BookDetails details, String desc)
    {
        super(desc);
        this.details = details;
    }
}