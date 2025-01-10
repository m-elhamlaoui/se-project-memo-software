package com.example.taskblock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.taskblock.dto.TaskDto;
import com.example.taskblock.model.task.Task;
import com.example.taskblock.model.taskblock.TaskBlock;
import com.example.taskblock.model.taskblock.TaskBlockGroup;
import com.example.taskblock.model.user.Member;
import com.example.taskblock.repository.*;
import com.example.taskblock.service.NotificationService;
import com.example.taskblock.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskBlockRepository taskBlockRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private TaskBlockGroupRepository taskBlockGroupRepository;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TaskService taskService;

    private TaskDto taskDto;
    private TaskBlock taskBlock;
    private Member creator;

    @BeforeEach
    void setUp() {
        creator = new Member();
        creator.setId(1L);

        taskBlock = new TaskBlock();
        taskBlock.setId(1L);
        taskBlock.setName("Test Block");
        taskBlock.setCreator(creator);
        taskBlock.setPercentageToAccept(75.0);
        taskBlock.setVoteDurationInSeconds(60);

        taskDto = new TaskDto();
        taskDto.setTitle("Test Task");
        taskDto.setDescription("Description");
        taskDto.setDurationSeconds(3600);
        taskDto.setTaggedGroupIds(Arrays.asList(1L));
        taskDto.setTaggedMemberNames(Arrays.asList("john", "jane"));

        // Common mocks
    }

    @Test
    void createTask_Success() {
        when(taskBlockRepository.findById(1L)).thenReturn(Optional.of(taskBlock));

        // Test-specific mocks
        Member member1 = new Member();
        member1.setHandle("john");
        Member member2 = new Member();
        member2.setHandle("jane");
        TaskBlockGroup group1 = new TaskBlockGroup();
        group1.setId(1L);

        when(taskBlockGroupRepository.findByTaskBlockIdAndGroupIds(eq(1L), anyList()))
                .thenReturn(Arrays.asList(group1));
        when(memberRepository.findByHandleIn(anyList()))
                .thenReturn(Arrays.asList(member1, member2));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> {
            Task t = i.getArgument(0);
            t.setId(1L);
            return t;
        });

        Task result = taskService.createTask(creator, 1L, taskDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test Task", result.getTitle());
        assertEquals("Description", result.getDescription());
        assertEquals(taskBlock, result.getTaskBlock());
    }

    @Test
    void createTask_TaskBlockNotFound() {
        when(taskBlockRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(creator, 99L, taskDto);
        });
    }

    @Test
    void createTask_GroupNotFound() {
        when(taskBlockRepository.findById(1L)).thenReturn(Optional.of(taskBlock));

        when(taskBlockGroupRepository.findByTaskBlockIdAndGroupIds(anyLong(), anyList()))
                .thenReturn(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(creator, 1L, taskDto);
        });
    }

    @Test
    void createTask_MemberNotFound() {

        //when(memberRepository.findByHandleIn(anyList())).thenReturn(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(creator, 1L, taskDto);
        });
    }
}