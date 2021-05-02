package pl.polsl.s15.library.domain.stock.books;

import pl.polsl.s15.library.domain.stock.StockItem;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Book extends StockItem {

    @ManyToOne
    @JoinColumn(name = "details_id")
    private BookDetails details;
}
