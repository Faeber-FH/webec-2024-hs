package ch.fhnw.webec.wishlist.service;

import ch.fhnw.webec.wishlist.db.WishRepository;
import ch.fhnw.webec.wishlist.model.Category;
import org.springframework.stereotype.Service;


@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

	public long countWishesByCategory(Category category) {
		return wishRepository.countWishesForCategory(category.getId());
	}

}
