package ua.com.foxminded.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Department;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.util.DataSourceFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupDaoTest {

    private final DriverManagerDataSource dataSource = DataSourceFactory.getInstance().initDataSource();
    private final SqlRunner sqlRunner = new SqlRunner(dataSource);
    private final GroupDao groupDao = new GroupDao(dataSource);
    private Group expectedGroup;
    private Group actualGroup;

    @BeforeEach
    void initTables() {
        sqlRunner.createTables();
    }

    @Test
    void create_shouldCreateGroup() {

        assertEquals(0, groupDao.getAll().size());

        expectedGroup = new Group(1L, 1L, 2);
        actualGroup = groupDao.create(expectedGroup);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void getById_shouldReturnGroup() {

        assertEquals(0, groupDao.getAll().size());
        Department department = new Department(1, "IT");
        Course course = new Course(2021);
        Group group1 = new Group(department.getId(), course.getId(), 1);
        Group group2 = new Group(department.getId(), course.getId(), 2);
        Group group3 = new Group(department.getId(), course.getId(), 3);
        groupDao.create(group1);
        groupDao.create(group2);
        groupDao.create(group3);

        expectedGroup = group2;
        groupDao.getById(expectedGroup.getId()).ifPresent(group -> actualGroup = group);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test
    void getAll_shouldReturnAllGroups() {

        assertEquals(0, groupDao.getAll().size());
        Department department = new Department(1, "IT");
        Course course = new Course(2021);
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
        Department department = new Department(1, "IT");
        Course course = new Course(2021);
        Group group = groupDao.create(new Group(department.getId(), course.getId(), 1));

        groupDao.delete(group.getId());

        assertEquals(0, groupDao.getAll().size());
    }
}
