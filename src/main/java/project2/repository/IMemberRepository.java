package project2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project2.model.Member;
import java.util.Optional;
@Repository
public interface IMemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findAll(Pageable pageable);

    /* Get member by account id */
    @Query(value = "select * from member where member.id_account = ?1" , nativeQuery = true)
    Optional<Member> getMemberByAccountId(Long id);

    @Query(value = "SELECT * " +
            "FROM `member` " +
            "inner join `rank` on `rank`.id_rank = `member`.id_rank " +
            "inner join `account` on `account`.id_account = `member`.id_account " +
            "where (`member`.name_member like concat('%' ,?1,'%') or ?1 is null ) " +
            "and (`member`.email_member like concat('%' ,?2,'%') or ?2 is null ) " +
            "and (`member`.address_member like concat('%' ,?3,'%') or ?3 is null ) " +
            "and (`member`.phone_member like concat('%' ,?4,'%') or ?4 is null ) " +
            "and (`rank`.name_rank like concat('%' ,?5,'%') or ?5 is null )" +
            "and `account`.flag_delete = 0",
            countQuery = "SELECT `rank`.id_rank " +
                    "FROM `member` " +
                    "inner join `rank` on `rank`.id_rank = `member`.id_rank " +
                    "inner join `account` on `account`.id_account = `member`.id_account " +
                    "where (`member`.name_member like concat('%' ,?1,'%') or ?1 is null ) " +
                    "and (`member`.email_member like concat('%' ,?2,'%') or ?2 is null ) " +
                    "and (`member`.address_member like concat('%' ,?3,'%') or ?3 is null ) " +
                    "and (`member`.phone_member like concat('%' ,?4,'%') or ?4 is null ) " +
                    "and (`rank`.name_rank like concat('%' ,?5,'%') or ?5 is null )" +
                    "and `account`.flag_delete = 0", nativeQuery = true)
    Page<Member> searchAllMember(String name, String email, String address, String phoneNumber, String nameRank, Pageable pageable);

    //SonLT View-Member
    @Query(value = "select nameMember from Member")
    void getTransactionByMember();
    //SonLT View-Member
    Member findMemberByAccount_IdAccount(Long id);
}

