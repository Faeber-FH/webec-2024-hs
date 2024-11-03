package ch.fhnw.webec.wishlist.db;

import ch.fhnw.webec.wishlist.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WishRepository extends JpaRepository<Wish, Integer> {

    @Query(value = "SELECT COUNT(*) FROM WISH_CATEGORIES  WHERE CATEGORIES_ID = ?1", nativeQuery = true)
    int countWishesForCategory(int categoryId);

}
