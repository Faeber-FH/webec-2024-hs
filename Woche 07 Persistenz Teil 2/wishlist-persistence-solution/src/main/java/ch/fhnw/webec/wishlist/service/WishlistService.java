package ch.fhnw.webec.wishlist.service;

import ch.fhnw.webec.wishlist.db.WishlistRepository;
import ch.fhnw.webec.wishlist.model.Category;
import ch.fhnw.webec.wishlist.model.Wishlist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

	private final WishlistRepository wishlistRepository;

	public WishlistService(WishlistRepository wishlistRepository) {
		this.wishlistRepository = wishlistRepository;
	}

	public List<Wishlist> findAll() {
		return wishlistRepository.findAll();
	}

	public Optional<Wishlist> findById(int id) {
		return wishlistRepository.findById(id);
	}

	public Wishlist save(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
		return wishlist;
	}

	public void delete(Wishlist wishlist) {
        wishlistRepository.delete(wishlist);
	}
}
