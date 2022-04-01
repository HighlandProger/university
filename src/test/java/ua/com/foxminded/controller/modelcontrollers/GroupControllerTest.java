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
import ua.com.foxminded.service.CourseService;
import ua.com.foxminded.service.DepartmentService;
import ua.com.foxminded.service.GroupService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class GroupControllerTest {

    private static final String TEMPLATE_URL = "/groups";
    private static final String VIEW_FOLDER_NAME = "groups";
    private static final String INDEX_VIEW = "/index";
    private static final String NEW_VIEW = "/new";
    private static final String EDIT_VIEW = "/edit";
    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
    private static final String ENTITIES_ATTRIBUTE_NAME = "entities";
    private static final String ID = "id";
    private static final String DEPARTMENT_ID = "departmentId";
    private static final String DEPARTMENT = "department";
    private static final String COURSE_ID = "courseId";
    private static final String COURSE = "course";
    private static final String GROUP_NUMBER = "groupNumber";
    private static final String SLASH = "/";

    private final Department randomDepartment = new Department(1L, "Economic");
    private final Course randomCourse = new Course(2L, 2021);
    private final List<Group> randomGroups = Arrays.asList(
        new Group(1L, randomDepartment, randomCourse, 4),
        new Group(2L, randomDepartment, randomCourse, 7));

    private final Group randomGroup = randomGroups.get(0);

    @Mock
    GroupService groupService;
    @Mock
    DepartmentService departmentService;
    @Mock
    CourseService courseService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new GroupController(groupService, departmentService, courseService)).build();
    }

    @Test
    void index_shouldReturnGroupsIndexView() throws Exception {

        when(groupService.getAll()).thenReturn(randomGroups);

        mockMvc.perform(get(TEMPLATE_URL)).andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasSize(randomGroups.size())))
            .andExpect(model().attribute(
                ENTITIES_ATTRIBUTE_NAME, hasItem(allOf(
                    hasProperty(ID, is(randomGroups.get(0).getId())),
                    hasProperty(DEPARTMENT, is(randomDepartment)),
                    hasProperty(COURSE, is(randomCourse)),
                    hasProperty(GROUP_NUMBER, is(randomGroups.get(0).getGroupNumber()))))))
            .andExpect(model().attribute(
                ENTITIES_ATTRIBUTE_NAME, hasItem(allOf(
                    hasProperty(ID, is(randomGroups.get(1).getId())),
                    hasProperty(DEPARTMENT, is(randomDepartment)),
                    hasProperty(COURSE, is(randomCourse)),
                    hasProperty(GROUP_NUMBER, is(randomGroups.get(1).getGroupNumber()))))));

        verify(groupService, times(1)).getAll();
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void newEntity_shouldReturnNewEntityView() throws Exception {

        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(COURSE_ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_NUMBER, is(0))));
    }

    @Test
    void edit_shouldReturnGroupEditView() throws Exception {

        when(groupService.getById(randomGroup.getId())).thenReturn(randomGroup);

        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomGroup.getId() + EDIT_VIEW)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomGroup.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(randomGroup.getDepartment().getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(COURSE_ID, is(randomGroup.getCourse().getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_NUMBER, is(randomGroup.getGroupNumber()))));

        verify(groupService, times(1)).getById(randomGroup.getId());
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void create_shouldCreateGroupAndRedirect() throws Exception {

        when(groupService.create(new Group(
            randomGroup.getDepartment(),
            randomGroup.getCourse(),
            randomGroup.getGroupNumber())))
            .thenReturn(randomGroup);

        when(departmentService.getById(randomGroup.getDepartment().getId()))
            .thenReturn(randomDepartment);

        when(courseService.getById(randomGroup.getCourse().getId()))
            .thenReturn(randomCourse);

        mockMvc.perform(post(TEMPLATE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(DEPARTMENT_ID, String.valueOf(randomGroup.getDepartment().getId()))
                .param(COURSE_ID, String.valueOf(randomGroup.getCourse().getId()))
                .param(GROUP_NUMBER, String.valueOf(randomGroup.getGroupNumber())))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(randomGroup.getDepartment().getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(COURSE_ID, is(randomGroup.getCourse().getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_NUMBER, is(randomGroup.getGroupNumber()))));

        verify(groupService, times(1)).create(new Group(randomGroup.getDepartment(), randomGroup.getCourse(), randomGroup.getGroupNumber()));
        verify(departmentService, times(1)).getById(randomGroup.getDepartment().getId());
        verify(courseService, times(1)).getById(randomGroup.getCourse().getId());
    }

    @Test
    void update_shouldUpdateGroupAndRedirect() throws Exception {

        Department changedRandomDepartment = new Department(3L, "Hi-tech");
        Course changedRandomCourse = new Course(3L, 2042);
        int changedRandomGroupNumber = randomGroup.getGroupNumber() + 3;
        Group changedRandomGroup = new Group(
            randomGroup.getId(),
            changedRandomDepartment,
            changedRandomCourse,
            changedRandomGroupNumber);

        when(departmentService.getById(changedRandomDepartment.getId())).thenReturn(changedRandomDepartment);
        when(courseService.getById(changedRandomCourse.getId())).thenReturn(changedRandomCourse);

        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomGroup.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(DEPARTMENT_ID, String.valueOf(changedRandomDepartment.getId()))
                .param(COURSE_ID, String.valueOf(changedRandomCourse.getId()))
                .param(GROUP_NUMBER, String.valueOf(changedRandomGroupNumber)))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomGroup.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(changedRandomDepartment.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(COURSE_ID, is(changedRandomCourse.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_NUMBER, is(changedRandomGroupNumber))));

        verify(groupService, times(1)).update(changedRandomGroup);
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void delete_shouldDeleteGroupAndRedirect() throws Exception {

        mockMvc.perform(delete(TEMPLATE_URL + SLASH + randomGroup.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(ID, String.valueOf(randomGroup.getId())))
            .andExpect(status().is3xxRedirection());

        verify(groupService, times(1)).delete(randomGroup.getId());
        verifyNoMoreInteractions(groupService);
    }
}
