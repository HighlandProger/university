package ua.com.foxminded.controller.modelcontrollers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.DepartmentService;
import ua.com.foxminded.service.TeacherService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class TeacherControllerTest {

    private static final String TEMPLATE_URL = "/teachers";
    private static final String VIEW_FOLDER_NAME = "teachers";
    private static final String INDEX_VIEW = "/index";
    private static final String NEW_VIEW = "/new";
    private static final String EDIT_VIEW = "/edit";
    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
    private static final String ENTITIES_ATTRIBUTE_NAME = "entities";
    private static final String ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String AGE = "age";
    private static final String DEPARTMENT_ID = "departmentId";
    private static final String DEPARTMENT = "department";
    private static final String SLASH = "/";

    private final Department randomDepartment = new Department(4L, "IT");
    private final List<Teacher> randomTeachers = Arrays.asList(
        new Teacher(1L, "Robert", "Kiyosaki", 58, randomDepartment),
        new Teacher(2L, "Djordan", "Belfort", 67, randomDepartment));

    private final Teacher randomTeacher = randomTeachers.get(0);

    @Mock
    TeacherService teacherService;
    @Mock
    DepartmentService departmentService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TeacherController(teacherService, departmentService)).build();
    }

    @Test
    void index_shouldReturnTeachersIndexView() throws Exception {

        when(teacherService.getAll()).thenReturn(randomTeachers);

        mockMvc.perform(get(TEMPLATE_URL))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasSize(randomTeachers.size())))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomTeacher.getId())),
                    hasProperty(FIRST_NAME, is(randomTeachers.get(0).getFirstName())),
                    hasProperty(LAST_NAME, is(randomTeachers.get(0).getLastName())),
                    hasProperty(AGE, is(randomTeachers.get(0).getAge())),
                    hasProperty(DEPARTMENT, is(randomDepartment))))))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomTeachers.get(1).getId())),
                    hasProperty(FIRST_NAME, is(randomTeachers.get(1).getFirstName())),
                    hasProperty(LAST_NAME, is(randomTeachers.get(1).getLastName())),
                    hasProperty(AGE, is(randomTeachers.get(1).getAge())),
                    hasProperty(DEPARTMENT, is(randomDepartment))))));

        verify(teacherService, times(1)).getAll();
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void newEntity_shouldReturnNewEntityView() throws Exception {

        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW)).andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(0))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, nullValue())));
    }

    @Test
    void edit_shouldReturnTeachersEditView() throws Exception {

        when(teacherService.getById(randomTeacher.getId())).thenReturn(randomTeacher);

        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomTeacher.getId() + EDIT_VIEW)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomTeacher.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, is(randomTeacher.getFirstName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, is(randomTeacher.getLastName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(randomTeacher.getAge()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(randomTeacher.getDepartment().getId()))));

        verify(teacherService, times(1)).getById(randomTeacher.getId());
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void create_shouldCreateTeacherAndRedirect() throws Exception {

        when(teacherService.create(new Teacher(
            randomTeacher.getFirstName(),
            randomTeacher.getLastName(),
            randomTeacher.getAge(),
            randomTeacher.getDepartment()
        ))).thenReturn(randomTeacher);

        when(departmentService.getById(randomTeacher.getDepartment().getId())).thenReturn(randomDepartment);

        mockMvc.perform(post(TEMPLATE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(FIRST_NAME, randomTeacher.getFirstName())
                .param(LAST_NAME, randomTeacher.getLastName())
                .param(AGE, String.valueOf(randomTeacher.getAge()))
                .param(DEPARTMENT_ID, String.valueOf(randomDepartment.getId())))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, is(randomTeacher.getFirstName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, is(randomTeacher.getLastName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(randomTeacher.getAge()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(randomDepartment.getId()))));

        verify(teacherService, times(1))
            .create(
                new Teacher(
                    randomTeacher.getFirstName(),
                    randomTeacher.getLastName(),
                    randomTeacher.getAge(),
                    randomDepartment));

        verify(departmentService, times(1)).getById(randomDepartment.getId());
    }

    @Test
    void update_shouldUpdateTeacherAndRedirect() throws Exception {

        Department changedDepartment = new Department(5L, "Physics");
        String changedFirstName = "Lana";
        String changedLastName = "Del-Ray";
        int changedAge = 32;
        Teacher changedTeacher = new Teacher(
            randomTeacher.getId(),
            changedFirstName,
            changedLastName,
            changedAge,
            changedDepartment);

        when(departmentService.getById(changedDepartment.getId())).thenReturn(changedDepartment);

        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomTeacher.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(FIRST_NAME, changedFirstName)
                .param(LAST_NAME, changedLastName)
                .param(AGE, String.valueOf(changedAge))
                .param(DEPARTMENT_ID, String.valueOf(changedDepartment.getId())))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomTeacher.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, is(changedFirstName))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, is(changedLastName))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(changedAge))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(changedDepartment.getId()))));

        verify(teacherService, times(1)).update(changedTeacher);
        verifyNoMoreInteractions(teacherService);
    }

    @Test
    void delete_shouldDeleteTeacherAndRedirect() throws Exception {

        mockMvc.perform(delete(TEMPLATE_URL + SLASH + randomTeacher.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(ID, String.valueOf(randomTeacher.getId())))
            .andExpect(status().is3xxRedirection());

        verify(teacherService, times(1)).delete(randomTeacher.getId());
        verifyNoMoreInteractions(teacherService);
    }
}
