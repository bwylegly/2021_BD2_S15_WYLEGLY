package pl.polsl.s15.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.polsl.s15.library.domain.ordering.OrderItem;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Modifying
    @Query("DELETE FROM OrderItem o1 " +
            "WHERE o1 IN " +
            "(SELECT o FROM Cart c JOIN c.orderItems o " +
            "WHERE o.itemId = :itemId AND c.id = :cartId)")
    void deleteByCartAndItemIds(Long cartId,Long itemId);
    @Modifying
    @Query("DELETE FROM OrderItem o1 " +
            "WHERE o1 IN " +
            "(SELECT o FROM Cart c JOIN c.orderItems o " +
            "WHERE c.id = :cartId)")
    void deleteAllByCartId(Long cartId);
    @Query("SELECT o1 FROM OrderItem o1 " +
            "WHERE o1 IN " +
            "(SELECT o FROM Cart c JOIN c.orderItems o " +
            "WHERE o.itemId = :itemId AND c.id = :cartId)")
    Optional<OrderItem> findByCartAndItemIds(Long cartId, Long itemId);
}
