package project2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import project2.model.Account;
import project2.model.Role;
import project2.repository.IAccountRepository;
import project2.repository.IRoleRepository;

import java.time.LocalDate;
import java.util.HashSet;

/**
 * Seeding data test for table users and roles
 */
@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IRoleRepository roleRepository;

    public static String EncrypPasswordUtils(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (roleRepository.findByNameRole("ROLE_MANAGER")==null){
            roleRepository.save(new Role("ROLE_MANAGER"));
        }

        if (roleRepository.findByNameRole("ROLE_MEMBER")==null){
            roleRepository.save(new Role("ROLE_MEMBER"));
        }
        if (roleRepository.findByNameRole("ROLE_BLOCK")==null){
            roleRepository.save(new Role("ROLE_BLOCK"));
        }
        // Them Quản lý
        if (accountRepository.findAccountByUsername("manager@aution.com") == null){
            Account manager = new Account();
            manager.setUsername("manager@aution.com");
            manager.setPassword(EncrypPasswordUtils("123123"));
            manager.setFlagDelete(false);
            manager.setLast_login(LocalDate.now());
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByNameRole("ROLE_MANAGER"));
            roles.add(roleRepository.findByNameRole("ROLE_MEMBER"));
            manager.setRoles(roles);
            accountRepository.save(manager);
        }
//


        //Them Người Dùng
        if (accountRepository.findAccountByUsername("member@aution.com") == null){
            Account member = new Account();
            member.setUsername("member@aution.com");
            member.setPassword(EncrypPasswordUtils("123123"));
            member.setFlagDelete(false);
            member.setLast_login(LocalDate.now());
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByNameRole("ROLE_MEMBER"));
            member.setRoles(roles);
            accountRepository.save(member);
        }

        if (accountRepository.findAccountByUsername("member1@aution.com") == null){
            Account member = new Account();
            member.setUsername("member1@aution.com");
            member.setPassword(EncrypPasswordUtils("123123"));
            member.setFlagDelete(false);
            member.setLast_login(LocalDate.now());
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByNameRole("ROLE_MEMBER"));
            member.setRoles(roles);
            accountRepository.save(member);
        }


        if (accountRepository.findAccountByUsername("member2@aution.com") == null){
            Account member = new Account();
            member.setUsername("member2@aution.com");
            member.setPassword(EncrypPasswordUtils("123123"));
            member.setFlagDelete(false);
            member.setLast_login(LocalDate.now());
            HashSet<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByNameRole("ROLE_MEMBER"));
            member.setRoles(roles);
            accountRepository.save(member);
        }
    }
}
