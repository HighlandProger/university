package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.exception.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    private final Group expectedGroup = new Group(1L,2L, 3L, 4);
    private final long randomId = 5;

    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupDao groupDao;

    @Test
    void create_shouldCallGroupDaoCreate() {

        groupService.create(expectedGroup);

        verify(groupDao).create(expectedGroup);
    }

    @Test
    void getById_shouldCallGroupDaoGetById() {

        when(groupDao.getById(expectedGroup.getId())).thenReturn(Optional.of(expectedGroup));

        groupService.getById(expectedGroup.getId());

        verify(groupDao).getById(expectedGroup.getId());
    }

    @Test
    void getById_shouldThrowEntityNotFoundException_whenIdIsNotPresent() {

        when(groupDao.getById(randomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
            () -> groupService.getById(randomId));
        String expectedString = "Object Group with id=" + randomId + " is not found";
        String actualString = exception.getMessage();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAll_shouldCallGroupDaoGetAll() {

        groupService.getAll();

        verify(groupDao).getAll();
    }

    @Test
    void delete_shouldCallGroupDaoDelete() {

        groupService.delete(randomId);

        verify(groupDao).delete(randomId);
    }

    @Test
    void  update_shouldCallGroupDaoUpdate() {

        groupService.update(randomId, expectedGroup);

        verify(groupDao).update(randomId, expectedGroup);
    }
}
