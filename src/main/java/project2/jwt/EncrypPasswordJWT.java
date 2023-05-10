package project2.jwt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project2.model.Account;

public class EncrypPasswordJWT {
    public static String EncrypPasswordUtils(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
