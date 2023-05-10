package project2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project2.model.Member;
import project2.repository.IMemberRepository;
import project2.service.IMemberService;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements IMemberService {
    @Autowired
    private IMemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Iterable<Member> saveAll(Iterable<Member> members) {
        return null;
    }

    @Override
    public Optional<Member> findByIdMember(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    @Override
    public List<Member> findAllList() {
        return memberRepository.findAll();
    }

    @Override
    public Page<Member> searchMember(String name, String email, String address, String phoneNumber, String nameRank, Pageable pageable) {
        return memberRepository.searchAllMember(name, email, address, phoneNumber, nameRank, pageable);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delele(Member member) {

    }

    /* Get member by account id */
    @Override
    public Optional<Member> getMemberByAccountId(Long accountId) {
        Optional<Member> member = this.memberRepository.getMemberByAccountId(accountId);
        if (member.isPresent()) {
            return this.memberRepository.getMemberByAccountId(accountId);
        }
        return null;
    }

    @Override
    public Member findByIdAccount(Long IdAccount) {
        return memberRepository.findMemberByAccount_IdAccount(IdAccount);
    }

    //SonLT View-Member
    @Override
    public Member findMemberByIdAccount(Long id) {
        return memberRepository.findMemberByAccount_IdAccount(id);
    }

    //SonLT Edit-Member
    @Override
    public void editMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void getTransactionMember() {
        memberRepository.getTransactionByMember();

    }
}