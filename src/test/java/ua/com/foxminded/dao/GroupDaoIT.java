package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.config.SpringDaoTestConfig;
import ua.com.foxminded.model.Course;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.model.Group;

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
class GroupDaoIT {

    private static final int GENERATED_GROUPS_COUNT = 6;

    @Autowired
    private GroupDao groupDao;

    private Group expectedGroup;

    @Test
    void create_shouldCreateGroup() {

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());

        expectedGroup = new Group(1L, new Department(1L, "IT"), new Course(3L, 2023), 2);
        Group actualGroup = groupDao.create(expectedGroup);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void getById_shouldReturnGroup() {

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());
        Department department = new Department(1L, "IT");
        Course course = new Course(1L, 2021);
        Group group1 = groupDao.create(new Group(department, course, 1));
        Group group2 = groupDao.create(new Group(department, course, 2));
        Group group3 = groupDao.create(new Group(department, course, 3));

        expectedGroup = group2;
        Optional<Group> actualGroup = groupDao.getById(expectedGroup.getId());

        assertTrue(actualGroup.isPresent());
        assertEquals(expectedGroup, actualGroup.get());
    }

    @Test
    void getAll_shouldReturnAllGroups() {

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());
        Department department = new Department(1L, "IT");
        Course course = new Course(1L, 2021);
        Group group1 = groupDao.create(new Group(department, course, 1));
        Group group2 = groupDao.create(new Group(department, course, 2));
        Group group3 = groupDao.create(new Group(department, course, 3));

        List<Group> expectedGroups = Arrays.asList(group1, group2, group3);
        List<Group> actualGroups = groupDao.getAll().stream().skip(GENERATED_GROUPS_COUNT).collect(Collectors.toList());

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void delete_shouldDeleteGroup() {

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());

        Group group = groupDao.create(new Group(null, null, 1));

        groupDao.delete(group.getId());

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());
    }

    @Test
    void update_shouldUpdateGroup() {

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());
        Department department = new Department(1L, "IT");
        Course course = new Course(1L, 2021);
        Group group = groupDao.create(new Group(department, course, 1));
        assertEquals(GENERATED_GROUPS_COUNT + 1, groupDao.getAll().size());

        int randomNumber = 4;
        int randomGroupNumber = group.getGroupNumber() + randomNumber;
        Department randomDepartment = new Department(department.getId() + randomNumber, department.getName());
        Course randomCourse = new Course(course.getId() + randomNumber, course.getEstablishYear());

        group.setDepartment(randomDepartment);
        group.setCourse(randomCourse);
        group.setGroupNumber(randomGroupNumber);

        groupDao.update(group);

        Optional<Group> updatedGroup = groupDao.getById(group.getId());

        assertTrue(updatedGroup.isPresent());
        assertEquals(updatedGroup.get(), group);
    }
}
