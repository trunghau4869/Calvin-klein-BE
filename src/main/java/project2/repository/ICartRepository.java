package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project2.model.Cart;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
    //QuangNV
    @Query(value = "select * from cart where id_member = ?1", nativeQuery = true)
    Cart getByIdMember(Long id_member);

//    @Query(nativeQuery = true, value = "SELECT * FROM Cart WHERE id_member = ?1")
//    Cart findByMember(Long idMember);


    @Modifying
    @Query(value = "insert into cart (warning, id_member) values (?1, ?2)", nativeQuery = true)
    @Transactional
    void createCart(String warning, Long idMember);
}
