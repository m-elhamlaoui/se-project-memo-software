package com.example.taskblock.service;

import com.example.taskblock.model.user.Member;
import com.example.taskblock.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final WebSocketNotificationService webSocketNotificationService;

    @Autowired
    public MemberService(MemberRepository memberRepository,WebSocketNotificationService webSocketNotificationService) {
        this.memberRepository = memberRepository;
        this.webSocketNotificationService = webSocketNotificationService;
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    public Optional<Member> getMemberByHandle(String handle) {
        return memberRepository.findByHandle(handle);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
    @Transactional
    public void inviteFriend(Long inviterId, Long inviteeId) {
        Member inviter = memberRepository.findById(inviterId).orElseThrow();
        Member invitee = memberRepository.findById(inviteeId).orElseThrow();

        // Check if there's already an invitation in the opposite direction
        if (inviter.getInviters().contains(invitee)) {
            // If there is, accept it instead of creating a new one
            acceptInvitation(inviterId, inviteeId);
            return;
        }

        // Check if they're already friends
        if (inviter.getFriends().contains(invitee)) {
            throw new RuntimeException("Already friends");
        }

        // Check if invitation already exists
        if (invitee.getInviters().contains(inviter)) {
            throw new RuntimeException("Invitation already exists");
        }

        // If all checks pass, create the invitation
        invitee.getInviters().add(inviter);
        memberRepository.save(invitee);
        webSocketNotificationService.sendUpdate(invitee, "/update/user/"+invitee.getEmail());

    }

    @Transactional
    public void acceptInvitation(Long accepterId, Long inviterId) {
        Member accepter = memberRepository.findById(accepterId).orElseThrow();
        Member inviter = memberRepository.findById(inviterId).orElseThrow();

        if (!accepter.getInviters().contains(inviter)) {
            throw new RuntimeException("No invitation exists");
        }

        // Remove the invitation
        accepter.getInviters().remove(inviter);

        // Add each other as friends
        accepter.getFriends().add(inviter);
        inviter.getFriends().add(accepter);



        memberRepository.save(accepter);
        memberRepository.save(inviter);
        webSocketNotificationService.sendUpdate(accepter, "/update/user/"+accepter.getEmail());
        webSocketNotificationService.sendUpdate(inviter, "/update/user/"+inviter.getEmail());

    }

    public void rejectInvitation(Long invitedId, Long inviterId) {
        Member invited = memberRepository.findById(invitedId)
                .orElseThrow(() -> new RuntimeException("Invited member not found"));
        Member inviter = memberRepository.findById(inviterId)
                .orElseThrow(() -> new RuntimeException("Inviter not found"));

        // Remove invitation
        if (!invited.getInviters().remove(inviter)) {
            throw new RuntimeException("No invitation found");
        }


        memberRepository.save(invited);
        webSocketNotificationService.sendUpdate(invited, "/update/user/"+invited.getEmail());
        
    }

    public List<Member> getFriends(Long memberId) {
        return memberRepository.findById(memberId)
                .map(Member::getFriends)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public List<Member> getInvitations(Long memberId) {
        return memberRepository.findById(memberId)
                .map(Member::getInviters)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    @Transactional
    public void removeFriend(Long currentUserId, Long friendId) {
    // Find the current user
    Member user = memberRepository.findById(currentUserId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    
    // Find the friend to remove
    Member friendToRemove = memberRepository.findById(friendId)
        .orElseThrow(() -> new RuntimeException("Friend not found"));
    
    // Remove friend from user's friend list
    user.getFriends().removeIf(friend -> friend.getId().equals(friendId));
    
    // Remove user from friend's friend list (if bidirectional)
    friendToRemove.getFriends().removeIf(friend -> friend.getId().equals(currentUserId));
    
    // Save both users
    memberRepository.save(user);
    memberRepository.save(friendToRemove);
    webSocketNotificationService.sendUpdate(user, "/update/user/"+user.getEmail());
    webSocketNotificationService.sendUpdate(friendToRemove, "/update/user/"+friendToRemove.getEmail());

}
}
