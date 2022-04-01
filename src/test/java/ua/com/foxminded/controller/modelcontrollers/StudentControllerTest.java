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
import ua.com.foxminded.model.Course;
import ua.com.foxminded.model.Department;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.GroupService;
import ua.com.foxminded.service.StudentService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class StudentControllerTest {

    private static final String TEMPLATE_URL = "/students";
    private static final String VIEW_FOLDER_NAME = "students";
    private static final String INDEX_VIEW = "/index";
    private static final String NEW_VIEW = "/new";
    private static final String EDIT_VIEW = "/edit";
    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
    private static final String ENTITIES_ATTRIBUTE_NAME = "entities";
    private static final String ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String AGE = "age";
    private static final String GROUP_ID = "groupId";
    private static final String GROUP = "group";
    private static final String SLASH = "/";

    private final Group randomGroup = new Group(1L, new Department(), new Course(), 3);
    private final List<Student> randomStudents = Arrays.asList(
        new Student(1L, "John", "Week", 35, randomGroup),
        new Student(2L, "Jack", "Bronson", 42, randomGroup));

    private final Student randomStudent = randomStudents.get(0);

    @Mock
    StudentService studentService;
    @Mock
    GroupService groupService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new StudentController(studentService, groupService)).build();
    }

    @Test
    void index_shouldReturnStudentsIndexView() throws Exception {

        when(studentService.getAll()).thenReturn(randomStudents);

        mockMvc.perform(get(TEMPLATE_URL)).andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasSize(randomStudents.size())))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomStudents.get(0).getId())),
                    hasProperty(FIRST_NAME, is(randomStudents.get(0).getFirstName())),
                    hasProperty(LAST_NAME, is(randomStudents.get(0).getLastName())),
                    hasProperty(AGE, is(randomStudents.get(0).getAge())),
                    hasProperty(GROUP, is(randomGroup))))))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomStudents.get(1).getId())),
                    hasProperty(FIRST_NAME, is(randomStudents.get(1).getFirstName())),
                    hasProperty(LAST_NAME, is(randomStudents.get(1).getLastName())),
                    hasProperty(AGE, is(randomStudents.get(1).getAge())),
                    hasProperty(GROUP, is(randomGroup))))));

        verify(studentService, times(1)).getAll();
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void newEntity_shouldReturnNewEntityView() throws Exception {

        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(0))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, nullValue())));
    }

    @Test
    void edit_shouldReturnStudentsEditView() throws Exception {

        when(studentService.getById(randomStudent.getId())).thenReturn(randomStudent);

        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomStudent.getId() + EDIT_VIEW)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomStudent.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, is(randomStudent.getFirstName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, is(randomStudent.getLastName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(randomStudent.getAge()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, is(randomStudent.getGroup().getId()))));

        verify(studentService, times(1)).getById(randomStudent.getId());
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void create_shouldCreateStudentAndRedirect() throws Exception {

        when(studentService.create(new Student(
            randomStudent.getFirstName(),
            randomStudent.getLastName(),
            randomStudent.getAge(),
            randomStudent.getGroup()
        ))).thenReturn(randomStudent);

        when(groupService.getById(randomStudent.getGroup().getId())).thenReturn(randomGroup);

        mockMvc.perform(post(TEMPLATE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(FIRST_NAME, randomStudent.getFirstName())
                .param(LAST_NAME, randomStudent.getLastName())
                .param(AGE, String.valueOf(randomStudent.getAge()))
                .param(GROUP_ID, String.valueOf(randomGroup.getId())))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, is(randomStudent.getFirstName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, is(randomStudent.getLastName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(randomStudent.getAge()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, is(randomStudent.getGroup().getId()))));

        verify(studentService, times(1))
            .create(new Student(
                randomStudent.getFirstName(),
                randomStudent.getLastName(),
                randomStudent.getAge(),
                randomGroup));
        verify(groupService, times(1)).getById(randomStudent.getGroup().getId());
    }

    @Test
    void update_shouldUpdateStudentAndRedirect() throws Exception {

        Group changedGroup = new Group(4L, null, null, 9);
        String changedFirstName = "Bob";
        String changedLastName = "Marley";
        int changedAge = 20;
        Student changedStudent = new Student(
            randomStudent.getId(),
            changedFirstName,
            changedLastName,
            changedAge,
            changedGroup);

        when(groupService.getById(changedGroup.getId())).thenReturn(changedGroup);

        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomStudent.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(FIRST_NAME, changedFirstName)
                .param(LAST_NAME, changedLastName)
                .param(AGE, String.valueOf(changedAge))
                .param(GROUP_ID, String.valueOf(changedGroup.getId())))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomStudent.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, is(changedFirstName))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, is(changedLastName))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(changedAge))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, is(changedGroup.getId()))));

        verify(studentService, times(1)).update(changedStudent);
        verifyNoMoreInteractions(studentService);
    }

    @Test
    void delete_shouldDeleteStudentAndRedirect() throws Exception {

        mockMvc.perform(delete(TEMPLATE_URL + SLASH + randomStudent.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(ID, String.valueOf(randomStudent.getId())))
            .andExpect(status().is3xxRedirection());

        verify(studentService, times(1)).delete(randomStudent.getId());
        verifyNoMoreInteractions(studentService);
    }
}
