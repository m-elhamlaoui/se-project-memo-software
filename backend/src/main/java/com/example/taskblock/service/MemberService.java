package com.example.taskblock.service;

import com.example.taskblock.model.user.Member;
import com.example.taskblock.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> getMemberByHandle(String handle) {
        return memberRepository.findByHandle(handle);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
