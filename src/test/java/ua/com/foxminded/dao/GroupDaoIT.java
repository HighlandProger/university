package ua.com.foxminded.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.com.foxminded.config.SpringDaoTestConfig;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Department;
import ua.com.foxminded.domain.Group;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringDaoTestConfig.class)
@Transactional
@Rollback
class GroupDaoIT {

    @Autowired
    private GroupDao groupDao;

    private Group expectedGroup;

    @Test
    void create_shouldCreateGroup() {

        assertEquals(0, groupDao.getAll().size());

        expectedGroup = new Group(1L, 1L, 1L, 2);
        Group actualGroup = groupDao.create(expectedGroup);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void getById_shouldReturnGroup() {

        assertEquals(0, groupDao.getAll().size());
        Department department = new Department(1L, "IT");
        Course course = new Course(1L, 2021);
        Group group1 = groupDao.create(new Group(department.getId(), course.getId(), 1));
        Group group2 = groupDao.create(new Group(department.getId(), course.getId(), 2));
        Group group3 = groupDao.create(new Group(department.getId(), course.getId(), 3));

        expectedGroup = group2;
        Optional<Group> actualGroup = groupDao.getById(expectedGroup.getId());

        assertTrue(actualGroup.isPresent());
        assertEquals(expectedGroup, actualGroup.get());
    }

    @Test
    void getAll_shouldReturnAllGroups() {

        assertEquals(0, groupDao.getAll().size());
        Department department = new Department(1L, "IT");
        Course course = new Course(1L, 2021);
        Group group1 = groupDao.create(new Group(department.getId(), course.getId(), 1));
        Group group2 = groupDao.create(new Group(department.getId(), course.getId(), 2));
        Group group3 = groupDao.create(new Group(department.getId(), course.getId(), 3));

        List<Group> expectedGroups = Arrays.asList(group1, group2, group3);
        List<Group> actualGroups = groupDao.getAll();

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void delete_shouldDeleteGroup() {

        assertEquals(0, groupDao.getAll().size());
        Department department = new Department("IT");
        Course course = new Course(2021);
        Group group = groupDao.create(new Group(department.getId(), course.getId(), 1));

        groupDao.delete(group.getId());

        assertEquals(0, groupDao.getAll().size());
    }
}
