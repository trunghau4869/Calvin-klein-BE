package project2.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project2.model.Account;
import project2.model.Member;

import java.util.List;
import java.util.Optional;

public interface IMemberService {
    Member save(Member member);

    Iterable<Member> saveAll(Iterable<Member> members);

    Optional<Member> findByIdMember(Long id);

    Page<Member> findAll(Pageable pageable);

    List<Member> findAllList();

    Page<Member> searchMember(String name, String email, String address, String phoneNumber, String nameRank, Pageable pageable);

    void deleteById(Long id);

    void delele(Member member);


    Optional<Member> getMemberByAccountId(Long accountId);

    //HauNT 
    Member findByIdAccount(Long IdAccount);

    //SonLT View-Member
    Member findMemberByIdAccount(Long id);

    //SonLT Edit-Member
    void editMember(Member member);

    void getTransactionMember();
}
