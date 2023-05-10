package project2.service;

import org.springframework.security.core.userdetails.UserDetails;
import project2.model.Account;
import project2.model.Member;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Account save(Account account);

    Iterable<Account> saveAll(Iterable<Account> accounts);

    Optional<Account> findById(Long id);

    List<Account> findAll();

    void deleteById(Long id);

    void delele(Account account);

    // HuyNN find account by member
    Account findByMember(Member member);

    //VinhTQ forgot password
    void saveForgotPassword(Account account,String password);
    void updateToken(Account account);
    Account findAccountByToken(String token);
    Account findAccountByEmailAndUsername(String email,String username);

    UserDetails loadUserByUsername(String username);

    Account getAccountByUsername(String username);

    /* Get accounts by role name -TuanNHA */
    List<Account> getAccountsByRoleName();

    //HauNT
    Account findAccountBlock(String username);
}
