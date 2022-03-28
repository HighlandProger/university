package ua.com.foxminded.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.foxminded.dao.ClassRoomDao;
import ua.com.foxminded.exception.EntityNotFoundException;
import ua.com.foxminded.model.ClassRoom;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassRoomServiceTest {


    private final ClassRoom expectedClassRoom = new ClassRoom(1L, "1");
    private final long randomId = 5;

    @InjectMocks
    private ClassRoomService classRoomService;

    @Mock
    private ClassRoomDao classRoomDao;

    @Test
    void create_shouldCallClassRoomDaoCreate() {

        classRoomService.create(expectedClassRoom);

        verify(classRoomDao).create(expectedClassRoom);
    }

    @Test
    void getById_shouldCallClassRoomDaoGetById() {

        when(classRoomDao.getById(expectedClassRoom.getId())).thenReturn(Optional.of(expectedClassRoom));

        classRoomService.getById(expectedClassRoom.getId());

        verify(classRoomDao).getById(expectedClassRoom.getId());
    }

    @Test
    void getById_shouldThrowEntityNotFoundException_whenIdIsNotPresent() {

        when(classRoomDao.getById(randomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
            () -> classRoomService.getById(randomId));
        String expectedString = "Object ClassRoom with id=" + randomId + " is not found";
        String actualString = exception.getMessage();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getAll_shouldCallClassRoomDaoGetAll() {

        classRoomService.getAll();

        verify(classRoomDao).getAll();
    }

    @Test
    void delete_shouldCallClassRoomDaoDelete() {

        classRoomService.delete(randomId);

        verify(classRoomDao).delete(randomId);
    }

    @Test
    void update_shouldCallClassRoomDaoUpdate() {

//        classRoomService.update(randomId, expectedClassRoom);

//        verify(classRoomDao).update(randomId, expectedClassRoom);
    }
}
