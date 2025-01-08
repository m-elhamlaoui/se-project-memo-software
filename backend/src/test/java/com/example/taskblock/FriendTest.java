package com.example.taskblock;

import com.example.taskblock.model.user.Member;
import com.example.taskblock.repository.MemberRepository;
import com.example.taskblock.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class FriendTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private Member createTestMember(String email, String handle) {
        Member member = new Member();
        member.setEmail(email);
        member.setHandle(handle);
        member.setPassword("password123");
        return memberRepository.save(member);
    }

    @Test
    public void testCyclicFriendshipInvitations() {
        // Test circular friend requests (A -> B -> C -> A)
        Member memberA = createTestMember("memberA@example.com", "memberA");
        Member memberB = createTestMember("memberB@example.com", "memberB");
        Member memberC = createTestMember("memberC@example.com", "memberC");

        memberService.inviteFriend(memberA.getId(), memberB.getId());
        memberService.inviteFriend(memberB.getId(), memberC.getId());
        memberService.inviteFriend(memberC.getId(), memberA.getId());

        // Accept all invitations
        memberService.acceptInvitation(memberB.getId(), memberA.getId());
        memberService.acceptInvitation(memberC.getId(), memberB.getId());
        memberService.acceptInvitation(memberA.getId(), memberC.getId());

        // Verify the circular friendship is established correctly
        Member finalA = memberRepository.findById(memberA.getId()).orElseThrow();
        Member finalB = memberRepository.findById(memberB.getId()).orElseThrow();
        Member finalC = memberRepository.findById(memberC.getId()).orElseThrow();

        assertTrue(finalA.getFriends().containsAll(List.of(memberB, memberC)));
        assertTrue(finalB.getFriends().containsAll(List.of(memberA, memberC)));
        assertTrue(finalC.getFriends().containsAll(List.of(memberA, memberB)));
    }

    @Test
    public void testMaxFriendshipLimit() {
        // Test system behavior when reaching maximum friendship limit
        Member mainMember = createTestMember("main@example.com", "mainUser");

        // Create 100 potential friends
        List<Member> potentialFriends = IntStream.range(0, 100)
                .mapToObj(i -> createTestMember(
                        "friend" + i + "@example.com",
                        "friend" + i
                ))
                .toList();

        // Send invitations to all potential friends
        potentialFriends.forEach(friend ->
                memberService.inviteFriend(mainMember.getId(), friend.getId())
        );

        // Accept all invitations
        potentialFriends.forEach(friend ->
                memberService.acceptInvitation(friend.getId(), mainMember.getId())
        );

        Member updatedMain = memberRepository.findById(mainMember.getId()).orElseThrow();
        assertEquals(100, updatedMain.getFriends().size());
    }

    @Test
    public void testInvitationAfterRejection() {
        // Test re-invitation after initial rejection
        Member member1 = createTestMember("rejector1@example.com", "rejector1");
        Member member2 = createTestMember("rejected1@example.com", "rejected1");

        // First invitation cycle
        memberService.inviteFriend(member1.getId(), member2.getId());
        memberService.rejectInvitation(member2.getId(), member1.getId());

        // Try to invite again
        memberService.inviteFriend(member1.getId(), member2.getId());

        Member updatedMember2 = memberRepository.findById(member2.getId()).orElseThrow();
        assertTrue(updatedMember2.getInviters().contains(member1));

        // Accept the second invitation
        memberService.acceptInvitation(member2.getId(), member1.getId());

        Member finalMember1 = memberRepository.findById(member1.getId()).orElseThrow();
        Member finalMember2 = memberRepository.findById(member2.getId()).orElseThrow();

        assertTrue(finalMember1.getFriends().contains(member2));
        assertTrue(finalMember2.getFriends().contains(member1));
    }

    @Test
    public void testInvitationToNonexistentUser() {
        // Test invitation to a non-existent user ID
        Member member = createTestMember("exists@example.com", "existingUser");
        Long nonExistentId = 99999L;

        assertThrows(RuntimeException.class, () -> {
            memberService.inviteFriend(member.getId(), nonExistentId);
        });
    }

    @Test
    public void testAcceptInvitationWithoutInvite() {
        // Test accepting a non-existent invitation
        Member member1 = createTestMember("noninviter@example.com", "nonInviter");
        Member member2 = createTestMember("nonreceiver@example.com", "nonReceiver");

        assertThrows(RuntimeException.class, () -> {
            memberService.acceptInvitation(member2.getId(), member1.getId());
        });

        Member updatedMember1 = memberRepository.findById(member1.getId()).orElseThrow();
        Member updatedMember2 = memberRepository.findById(member2.getId()).orElseThrow();

        assertTrue(updatedMember1.getFriends().isEmpty());
        assertTrue(updatedMember2.getFriends().isEmpty());
    }
}