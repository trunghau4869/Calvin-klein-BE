package project2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project2.model.Account;
import project2.model.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    /* Get account by username */
    @Query(value = "select * from `account` where `account`.`username` = ?1" , nativeQuery = true)
    Account getAccByUsername(String username);


    /* Get accounts by role name -TuanNHA */
    @Query(value = "select account.* from `account` \n" +
            "inner join account_role\n" +
            "on account_role.id_account = `account`.id_account\n" +
            "group by `account`.id_account\n" +
            "having count(account_role.id_role) = 1" , nativeQuery=true)
    List<Account> getAccountsByRoleMember();

    Account findByMember(Member member);

    //    @Query(value = "select * from account where toke = ?1",nativeQuery = true)
    Account findAccountByToken(String token);

    @Query(value = "select * from account \n" +
            "join member on member.id_account = account.id_account\n" +
            "where account.username = ?1 and member.email_member = ?2",nativeQuery = true)
    Account findAccountByUsernameAndEnmail(String username, String email);

//    @Query(value = "select * from `account`\n" +
//            "inner join `member` on `member`.id_account=`account`.id_account\n" +
//            "inner join account_role on account_role.id_account = `account`.id_account\n" +
//            "inner join `role` on `role`.id_role=account_role.id_role\n" +
//            "where `account`.`block`=false and `account`.username=:username",nativeQuery = true)
    Account findAccountByUsername(String username);

    Account getAccountByUsername(String username);
    @Query(value = "select * from `account` where `account`.username=:username and `account`.`block`=1",nativeQuery = true)
    Account findAccountBlockByUsername(String username);
}