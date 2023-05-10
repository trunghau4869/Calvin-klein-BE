package project2.dto;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class ValidateAccountDTO implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountMemberDTO accountMemberDTO = (AccountMemberDTO) target;
        if (accountMemberDTO.getUsername() == null) {
            errors.rejectValue("username", "username.null", "Username not empty!");
        } else if (!Pattern.compile("^[a-zA-z0-9]{5,20}$").matcher(accountMemberDTO.getUsername()).find()) {
            errors.rejectValue("username", "username.pattern", "Username invalid format");
        }
    }
}
