package com.example.taskblock.controller;

import com.example.taskblock.model.user.Member;
import com.example.taskblock.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    private final MemberService memberService;

    public FriendController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/invite/{invitedId}")
    public ResponseEntity<Void> inviteFriend(
            @AuthenticationPrincipal Member inviter,
            @PathVariable Long invitedId) {
        memberService.inviteFriend(inviter.getId(), invitedId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept/{inviterId}")
    public ResponseEntity<Void> acceptInvitation(
            @AuthenticationPrincipal Member invited,
            @PathVariable Long inviterId) {
        memberService.acceptInvitation(invited.getId(), inviterId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{inviterId}")
    public ResponseEntity<Void> rejectInvitation(
            @AuthenticationPrincipal Member invited,
            @PathVariable Long inviterId) {
        memberService.rejectInvitation(invited.getId(), inviterId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Member>> getFriends(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(memberService.getFriends(member.getId()));
    }

    @GetMapping("/invitations")
    public ResponseEntity<List<Member>> getInvitations(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(memberService.getInvitations(member.getId()));
    }
}
