package ch.fhnw.webec.wishlist.db;

import ch.fhnw.webec.wishlist.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

}
