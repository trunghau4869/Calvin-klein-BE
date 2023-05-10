package project2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

@Configuration
public class SmtpAuthenticator extends Authenticator {
    public SmtpAuthenticator() {
        super();
    }

    @Bean
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        String username = EmailAccountConfig.EMAIL;
        String password = EmailAccountConfig.PASSWORD;
        if ((username != null) && (username.length() > 0) && (password != null) && (password.length() > 0)) {
            return new PasswordAuthentication(username, password);
        }
        return null;
    }
}
