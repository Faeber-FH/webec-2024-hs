package ch.fhnw.webec.wishlist;

import ch.fhnw.webec.wishlist.db.CategoryRepository;
import ch.fhnw.webec.wishlist.db.WishRepository;
import ch.fhnw.webec.wishlist.db.WishlistRepository;
import ch.fhnw.webec.wishlist.model.Category;
import ch.fhnw.webec.wishlist.model.Wish;
import ch.fhnw.webec.wishlist.model.Wishlist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class WishRepositoryIT {

    private final WishlistRepository wishlistRepository;
    private final CategoryRepository categoryRepository;
    private final WishRepository wishRepository;

    @Autowired
	public WishRepositoryIT(WishlistRepository wishlistRepository, CategoryRepository categoryRepository, WishRepository wishRepository) {
        this.wishlistRepository = wishlistRepository;
        this.categoryRepository = categoryRepository;
        this.wishRepository = wishRepository;
    }
	
	@Test
	void countWishesByCategory() {
		Category c = new Category("expensive");
		categoryRepository.save(c);
		Wishlist wl = new Wishlist("2024");
		wl.getEntries().add(new Wish("bike", "", 1, List.of(c)));
		wl.getEntries().add(new Wish("camera", "", 2, List.of(c)));
		wishlistRepository.save(wl);

		var count = wishRepository.countByCategoriesContaining(c);

		assertEquals(2, count);
	}

}
