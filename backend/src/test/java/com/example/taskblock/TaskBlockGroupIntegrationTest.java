package com.example.taskblock;

import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.taskblock.TaskBlockGroup;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.repository.MemberRepository;
import com.example.taskblock.repository.TaskBlockRepository;
import com.example.taskblock.repository.TaskBlockGroupRepository;
import com.example.taskblock.repository.TaskRepository;
import com.example.taskblock.service.TaskBlockGroupService;
import com.example.taskblock.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskblock.model.task.TaskStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional


public class TaskBlockGroupIntegrationTest {
    @Autowired
    private TaskBlockGroupService taskBlockGroupService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskBlockRepository taskBlockRepository;

    @Autowired
    private TaskBlockGroupRepository taskBlockGroupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Member creator;
    private Member member1;
    private Member member2;
    private Member member3;
    private TaskBlock taskBlock;

    private Member createTestMember(String email, String handle) {
        Member member = new Member();
        member.setEmail(email);
        member.setHandle(handle);
        member.setPassword("password123");
        return memberRepository.save(member);
    }

    @BeforeEach
    void setUp() {
        // Create test members
        creator = new Member();
        creator.setEmail("creator@test.com");
        creator.setHandle("creator");
        creator.setPassword("password123");
        creator = memberRepository.save(creator);

        member1 = createTestMember("member1@test.com", "member1");
        member2 = createTestMember("member2@test.com", "member2");
        member3 = createTestMember("member3@test.com", "member3");

        // Create taskblock with all required fields
        taskBlock = new TaskBlock();
        taskBlock.setName("Test TaskBlock");
        taskBlock.setCreator(creator);
        taskBlock.setPercentageToAccept(75.0);
        taskBlock.setVoteDurationInSeconds(60);  // Changed to match TaskBlock entity
        taskBlock = taskBlockRepository.save(taskBlock);
    }

    @Test
    void testFullGroupWorkflow() {
        // 1. Create groups
        TaskBlockGroup developers = taskBlockGroupService.createGroup(
                taskBlock.getId(),
                creator.getId(),
                "Developers"
        );

        TaskBlockGroup testers = taskBlockGroupService.createGroup(
                taskBlock.getId(),
                creator.getId(),
                "Testers"
        );

        assertNotNull(developers.getId());
        assertNotNull(testers.getId());

        // 2. Add members to groups
        taskBlockGroupService.addMembersToGroup(
                taskBlock.getId(),
                developers.getId(),
                creator.getId(),
                Arrays.asList(member1.getId(), member2.getId())
        );

        taskBlockGroupService.addMembersToGroup(
                taskBlock.getId(),
                testers.getId(),
                creator.getId(),
                Arrays.asList(member2.getId(), member3.getId())
        );

        List<Member> devMembers = taskBlockGroupService.getGroupMembers(developers.getId());
        assertEquals(2, devMembers.size());
        assertTrue(devMembers.contains(member1));
        assertTrue(devMembers.contains(member2));

        List<Member> testMembers = taskBlockGroupService.getGroupMembers(testers.getId());
        assertEquals(2, testMembers.size());
        assertTrue(testMembers.contains(member2));
        assertTrue(testMembers.contains(member3));

        // 3. Create a task with required fields
        Task task = new Task("Implement feature", "New feature implementation", taskBlock, 3600);
        task.setStatus(TaskStatus.PENDING);
        task.addTaggedIndividual(creator);
        task = taskRepository.save(task);

        taskService.tagGroup(task.getId(), developers.getId());
        taskService.tagGroup(task.getId(), testers.getId());

        // 4. Verify tagged members and groups
        Task savedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertEquals(2, savedTask.getTaggedGroups().size());
        assertTrue(savedTask.getTaggedIndividuals().contains(creator));

        List<Member> allTaggedMembers = taskService.getAllTaggedMembers(task.getId());
        assertEquals(4, allTaggedMembers.size()); // creator + unique members from both groups

        // 5. Remove group tag from task
        taskService.untagGroup(task.getId(), testers.getId());
        savedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertEquals(1, savedTask.getTaggedGroups().size());

        // 6. Delete group
        taskBlockGroupService.deleteGroup(taskBlock.getId(), testers.getId(), creator.getId());
        List<TaskBlockGroup> remainingGroups = taskBlockGroupService.getGroupsInTaskBlock(taskBlock.getId());
        assertEquals(1, remainingGroups.size());
        assertEquals(developers.getId(), remainingGroups.get(0).getId());
    }

    @Test
    void testUnauthorizedOperations() {
        // Create a group first
        TaskBlockGroup group = taskBlockGroupService.createGroup(
                taskBlock.getId(),
                creator.getId(),
                "TestGroup"
        );

        // Try to modify group with non-creator member
        assertThrows(IllegalArgumentException.class, () -> {
            taskBlockGroupService.addMembersToGroup(
                    taskBlock.getId(),
                    group.getId(),
                    member1.getId(),
                    List.of(member2.getId())
            );
        });

        assertThrows(IllegalArgumentException.class, () -> {
            taskBlockGroupService.deleteGroup(
                    taskBlock.getId(),
                    group.getId(),
                    member1.getId()
            );
        });
    }

    @Test
    void testDuplicateGroupName() {
        taskBlockGroupService.createGroup(taskBlock.getId(), creator.getId(), "TestGroup");

        assertThrows(IllegalArgumentException.class, () -> {
            taskBlockGroupService.createGroup(taskBlock.getId(), creator.getId(), "TestGroup");
        });
    }
}