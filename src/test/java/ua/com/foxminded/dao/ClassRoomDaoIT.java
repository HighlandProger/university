package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.config.SpringDaoTestConfig;
import ua.com.foxminded.model.ClassRoom;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDaoTestConfig.class)
@Transactional
@Rollback
class ClassRoomDaoIT {

    private static final int GENERATED_CLASS_ROOMS_COUNT = 3;

    @Autowired
    private ClassRoomDao classRoomDao;

    private ClassRoom expectedClassRoom;

    @Test
    void create_shouldCreateClassRoom() {

        assertEquals(GENERATED_CLASS_ROOMS_COUNT, classRoomDao.getAll().size());

        expectedClassRoom = new ClassRoom(8L, "4");
        ClassRoom actualClassRoom = classRoomDao.create(expectedClassRoom);

        assertEquals(expectedClassRoom, actualClassRoom);
    }

    @Test
    void getById_shouldReturnClassRoom() {

        assertEquals(GENERATED_CLASS_ROOMS_COUNT, classRoomDao.getAll().size());
        ClassRoom classRoom1 = classRoomDao.create(new ClassRoom("1"));
        ClassRoom classRoom2 = classRoomDao.create(new ClassRoom("2"));
        ClassRoom classRoom3 = classRoomDao.create(new ClassRoom("3"));

        expectedClassRoom = classRoom2;
        Optional<ClassRoom> actualClassRoom = classRoomDao.getById(expectedClassRoom.getId());

        assertTrue(actualClassRoom.isPresent());
        assertEquals(expectedClassRoom, actualClassRoom.get());
    }

    @Test
    void getAll_shouldReturnAllClassRooms() {

        assertEquals(GENERATED_CLASS_ROOMS_COUNT, classRoomDao.getAll().size());
        ClassRoom classRoom1 = classRoomDao.create(new ClassRoom("1"));
        ClassRoom classRoom2 = classRoomDao.create(new ClassRoom("2"));
        ClassRoom classRoom3 = classRoomDao.create(new ClassRoom("3"));

        List<ClassRoom> expectedClassRooms = Arrays.asList(classRoom1, classRoom2, classRoom3);
        List<ClassRoom> actualClassRooms = classRoomDao.getAll().stream().skip(GENERATED_CLASS_ROOMS_COUNT).collect(Collectors.toList());

        assertEquals(expectedClassRooms, actualClassRooms);
    }

    @Test
    void delete_shouldDeleteClassRoom() {

        assertEquals(GENERATED_CLASS_ROOMS_COUNT, classRoomDao.getAll().size());
        ClassRoom classRoom = classRoomDao.create(new ClassRoom("1"));
        assertEquals(GENERATED_CLASS_ROOMS_COUNT + 1, classRoomDao.getAll().size());

        classRoomDao.delete(classRoom.getId());

        assertEquals(GENERATED_CLASS_ROOMS_COUNT, classRoomDao.getAll().size());
    }

    @Test
    void update_shouldUpdateClassRoom() {

        assertEquals(GENERATED_CLASS_ROOMS_COUNT, classRoomDao.getAll().size());
        ClassRoom classRoom1 = classRoomDao.create(new ClassRoom("1"));
        ClassRoom classRoom2 = new ClassRoom("2");
        assertEquals(GENERATED_CLASS_ROOMS_COUNT + 1, classRoomDao.getAll().size());

        classRoomDao.update(classRoom1.getId(), classRoom2);

        Optional<ClassRoom> updatedClassRoom = classRoomDao.getById(classRoom1.getId());

        assertTrue(updatedClassRoom.isPresent());
        assertEquals(classRoom1.getId(), updatedClassRoom.get().getId());
        assertEquals(classRoom2.getClassNumber(), updatedClassRoom.get().getClassNumber());
    }
}
