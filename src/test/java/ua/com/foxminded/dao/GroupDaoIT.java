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

        expectedGroup = new Group(1L, 1L, 1L, 2);
        Group actualGroup = groupDao.create(expectedGroup);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void getById_shouldReturnGroup() {

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());
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

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());
        Department department = new Department(1L, "IT");
        Course course = new Course(1L, 2021);
        Group group1 = groupDao.create(new Group(department.getId(), course.getId(), 1));
        Group group2 = groupDao.create(new Group(department.getId(), course.getId(), 2));
        Group group3 = groupDao.create(new Group(department.getId(), course.getId(), 3));

        List<Group> expectedGroups = Arrays.asList(group1, group2, group3);
        List<Group> actualGroups = groupDao.getAll().stream().skip(GENERATED_GROUPS_COUNT).collect(Collectors.toList());

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    void delete_shouldDeleteGroup() {

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());
        Department department = new Department("IT");
        Course course = new Course(2021);
        Group group = groupDao.create(new Group(department.getId(), course.getId(), 1));

        groupDao.delete(group.getId());

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());
    }

    @Test
    void update_shouldUpdateGroup() {

        assertEquals(GENERATED_GROUPS_COUNT, groupDao.getAll().size());
        Group group1 = groupDao.create(new Group(1L, 1L, 1));
        Group group2 = new Group(2L, 2L, 2);
        assertEquals(GENERATED_GROUPS_COUNT + 1, groupDao.getAll().size());

        groupDao.update(group1.getId(), group2);

        Optional<Group> updatedGroup = groupDao.getById(group1.getId());

        assertTrue(updatedGroup.isPresent());
        assertEquals(group1.getId(), updatedGroup.get().getId());
        assertEquals(group2.getDepartmentId(), updatedGroup.get().getDepartmentId());
        assertEquals(group2.getCourseId(), updatedGroup.get().getCourseId());
        assertEquals(group2.getGroupNumber(), updatedGroup.get().getGroupNumber());
    }
}
