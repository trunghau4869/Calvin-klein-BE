package project2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project2.model.Account;
import project2.model.Member;
import project2.service.impl.AccountService;
import project2.service.impl.MemberService;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project2.dto.AccountMemberDTO;
import project2.dto.ValidateAccountDTO;
import project2.jwt.EncrypPasswordJWT;
import project2.model.Account;
import project2.model.Member;
import project2.model.Rank;
import project2.model.Role;
import project2.service.IAccountService;
import project2.service.IMemberService;
import project2.service.IRankService;
import project2.service.IRoleService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@CrossOrigin(origins = "*")
public class MemberController {
    @Autowired
    private IRankService iRankService;

    @Autowired
    private IMemberService iMemberService;

    @Autowired
    private IRoleService iRoleService;


    @Autowired
    private IAccountService iAccountService;


    @Autowired
    private ValidateAccountDTO validateAccountDTO;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IRankService rankService;

    /* Get member by account id */
    @RequestMapping(value = "/account={accountId}", method = RequestMethod.GET)
    public ResponseEntity<Member> getMemberByAccountId(@PathVariable("accountId") Long accountId) {
        Optional<Member> member = this.memberService.getMemberByAccountId(accountId);
        if (member.isPresent()) {
            return new ResponseEntity<>(member.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/allMember")
    public ResponseEntity<Page<Member>> showListMember(@PageableDefault(size = 10) Pageable pageable) {
        Page<Member> memberPage = memberService.findAll(pageable);
        return new ResponseEntity<>(memberPage, HttpStatus.OK);
    }

    @GetMapping("/getAccount")
    public ResponseEntity<List<Account>> showListAccount() {
        List<Account> accountList = accountService.findAll();
        return new ResponseEntity<>(accountList, HttpStatus.OK);
    }

    @GetMapping("/allRankMember")
    public ResponseEntity<List<Rank>> showListRankMember() {
        List<Rank> rankMembers = rankService.findAllRank();
        return new ResponseEntity<>(rankMembers, HttpStatus.OK);
    }

    @GetMapping("/search/{name}/{email}/{phoneNumber}/{nameRank}/{address}")
    public ResponseEntity<Page<Member>> searchMember(@PathVariable String email, @PathVariable String name, @PathVariable String phoneNumber,
                                                     @PathVariable String address, @PathVariable String nameRank,
                                                     @PageableDefault(size = 10) Pageable pageable) {
        if (email.equals("null")) {
            email = null;
        }
        if (name.equals("null")) {
            name = null;
        }
        if (phoneNumber.equals("null")) {
            phoneNumber = null;
        }
        if (address.equals("null")) {
            address = null;
        }
        if (nameRank.equals("null")) {
            nameRank = null;
        }
        Page<Member> memberPageSearch = memberService.searchMember(name, email, address, phoneNumber, nameRank, pageable);
        return new ResponseEntity<>(memberPageSearch, HttpStatus.OK);
    }

    @PostMapping("/member/block")
    public ResponseEntity<?> blockMember(@RequestBody Long[] blockArray) {
        for (Long id : blockArray) {
            Member member = memberService.findByIdMember(id).get();
            member.getAccount().setBlock(true);
            iMemberService.save(member);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/member/unBlock")
    public ResponseEntity<?> unBlockMember(@RequestBody Long[] unBlockArray) {
        for (Long id : unBlockArray) {
            Member member = memberService.findByIdMember(id).get();
            member.getAccount().setBlock(false);
            iMemberService.save(member);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/member/delete")
    public ResponseEntity<?> deleteMember(@RequestBody Long[] blockArray) {
        for (Long id : blockArray) {
            Member member = memberService.findByIdMember(id).get();
            member.getAccount().setFlagDelete(true);
            iMemberService.save(member);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Bin code method create account member
    @PostMapping("/member/saveNewAccountMember")
    public ResponseEntity<?> saveAccountMember(@Valid @RequestBody AccountMemberDTO accountMemberDTO, BindingResult bindingResult) {
        Boolean check = true;
        validateAccountDTO.validate(accountMemberDTO, bindingResult);
        List<Account> accountList = iAccountService.findAll();
        for (Integer i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getUsername().equals(accountMemberDTO.getUsername())) {
                check = false;
                break;
            }
        }

        if (bindingResult.hasErrors() || !check) {
            if (bindingResult.hasErrors()) {
                return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.NOT_MODIFIED);
            } else {
                return new ResponseEntity<>("Tên tài khoản đã tồn tại", HttpStatus.NOT_MODIFIED);
            }
        } else {
            Member member = new Member();
            Account account = new Account();


            Set<Role> roles = new HashSet<>();
            /* Set data for Account */
            account.setUsername(accountMemberDTO.getUsername());
            account.setPassword(EncrypPasswordJWT.EncrypPasswordUtils(accountMemberDTO.getPassword()));

            /*Set row mặc định*/
            Role role = iRoleService.findByName("ROLE_MEMBER");
            roles.add(role);
            account.setRoles(roles);
            account.setFlagDelete(false);
            account.getLast_login(LocalDate.now());
            Account accountCreated = iAccountService.save(account);
            /* Set data for member */
            member.setAccount(iAccountService.findById(accountCreated.getIdAccount()).get());
            member.setNameMember(accountMemberDTO.getNameMember());
            member.setEmailMember(accountMemberDTO.getEmailMember());
            member.setAddressMember(accountMemberDTO.getCity() + "," + accountMemberDTO.getDistrict() + "," + accountMemberDTO.getWard());
            member.setDateOfBirthMember(accountMemberDTO.getDateOfBirthMember());
            member.setFlagDelete(false);
            member.setIdCardMember(accountMemberDTO.getIdCardMember());
            member.setPaypalMember(accountMemberDTO.getPaypalMember());
            member.setPhoneMember(accountMemberDTO.getPhoneMember());
            member.setCheckedClause(false);
            /*Set rank default*/
            Rank rank = iRankService.findByName("BROZER").get();
            member.setRank(rank);

            iMemberService.save(member);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    //bin code check username
    @GetMapping("/member/checkUsername")
    public ResponseEntity<List<Account>> checkUsername(@RequestParam String username) {
        List<Account> list = iAccountService.findAll();
        List<Account> accounts = new ArrayList<>();
        for (Integer i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(username)) {
                accounts.add(list.get(i));
                return new ResponseEntity<>(accounts, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //SonLT View-Member
    @GetMapping("/profile/{idAccount}")
    public ResponseEntity<Member> findMember(@PathVariable Long idAccount) {
        Member member = iMemberService.findMemberByIdAccount(idAccount);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(member, HttpStatus.OK);
        }
    }


    //SonLT Edit-Member
    @PatchMapping("/profile/edit")
    public ResponseEntity<Void> updateMember(@RequestBody Member member){
        System.out.println(member);
        iMemberService.editMember(member);
        return new ResponseEntity<Void>( HttpStatus.OK);
    }

    // HuyNN get member by id method
    @GetMapping("/getMemberById/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return new ResponseEntity<Member>(iMemberService.findByIdMember(id).get(), HttpStatus.OK);
    }
}



