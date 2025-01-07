package com.example.taskblock.controller;

import com.example.taskblock.model.user.Member;
import com.example.taskblock.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member createdMember = memberService.createMember(member);
        return ResponseEntity.ok(createdMember);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        Optional<Member> member = memberService.getMemberByEmail(email);
        return member.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/handle/{handle}")
    public ResponseEntity<Member> getMemberByHandle(@PathVariable String handle) {
        Optional<Member> member = memberService.getMemberByHandle(handle);
        return member.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
