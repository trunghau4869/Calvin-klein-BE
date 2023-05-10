package project2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project2.jwt.JwtUtility;
import project2.model.Account;
import project2.payload.JwtResponse;
import project2.payload.LoginRequest;
import project2.service.impl.AccountService;


@RestController
@CrossOrigin("http://localhost:4200/")
public class SecurityController   {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AccountService accountService;



    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = accountService
                .loadUserByUsername(authenticationRequest.getUsername());


        final String token = jwtUtility.generateJwtToken(userDetails.getUsername());

        Account account = accountService.getAccountByUsername(authenticationRequest.getUsername());

        return ResponseEntity.ok(new JwtResponse(token, account.getIdAccount(), account.getUsername(), account.getRoles()));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
