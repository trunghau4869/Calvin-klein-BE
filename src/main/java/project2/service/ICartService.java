package project2.service;
import project2.model.Cart;

public interface ICartService {
    Cart findByIdMember(Long id_member);
    void createCart(String warning, Long idMember);
    void updateCart(Cart cart);
    Cart findById(Long id);
}
