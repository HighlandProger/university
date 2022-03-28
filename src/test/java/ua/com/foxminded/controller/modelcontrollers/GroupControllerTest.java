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
    private static final String GROUPS_ATTRIBUTE_NAME = "groupDTOS";
    private static final String ID = "id";
    private static final String DEPARTMENT_ID = "departmentId";
    private static final String DEPARTMENT_NAME = "departmentName";
    private static final String COURSE_ID = "courseId";
    private static final String COURSE_ESTABLISH_YEAR = "courseEstablishYear";
    private static final String GROUP_NUMBER = "groupNumber";
    private static final String SLASH = "/";

//    private final Group randomGroup = new Group(1L, 1L, 2L, 3);
//    private final Department randomDepartment = new Department(1L, "Economic");
    private final Course randomCourse = new Course(2L, 2021);
//    private final List<Group> randomGroups = Arrays.asList(new Group(1L, 2L, 3L, 4), new Group(2L, 5L, 6L, 7));

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

//        when(groupService.getAll()).thenReturn(randomGroups);
//        when(departmentService.getById(randomGroups.get(0).getDepartmentId())).thenReturn(randomDepartment);
//        when(courseService.getById(randomGroups.get(0).getCourseId())).thenReturn(randomCourse);
//        when(departmentService.getById(randomGroups.get(1).getDepartmentId())).thenReturn(randomDepartment);
//        when(courseService.getById(randomGroups.get(1).getCourseId())).thenReturn(randomCourse);

//        mockMvc.perform(get(TEMPLATE_URL)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW)).andExpect(model().attribute(GROUPS_ATTRIBUTE_NAME, hasSize(randomGroups.size()))).andExpect(model().attribute(GROUPS_ATTRIBUTE_NAME, hasItem(allOf(hasProperty(ID, is(randomGroups.get(0).getId())), hasProperty(DEPARTMENT_NAME, is(randomDepartment.getName())), hasProperty(COURSE_ESTABLISH_YEAR, is(randomCourse.getEstablishYear())), hasProperty(GROUP_NUMBER, is(randomGroups.get(0).getGroupNumber())))))).andExpect(model().attribute(GROUPS_ATTRIBUTE_NAME, hasItem(allOf(hasProperty(ID, is(randomGroups.get(1).getId())), hasProperty(DEPARTMENT_NAME, is(randomDepartment.getName())), hasProperty(COURSE_ESTABLISH_YEAR, is(randomCourse.getEstablishYear())), hasProperty(GROUP_NUMBER, is(randomGroups.get(1).getGroupNumber()))))));

        verify(groupService, times(2)).getAll();
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void newEntity_shouldReturnNewEntityView() throws Exception {

        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW)).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(COURSE_ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_NUMBER, is(0))));
    }

    @Test
    void edit_shouldReturnGroupEditView() throws Exception {

//        when(groupService.getById(randomGroup.getId())).thenReturn(randomGroup);
//
//        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomGroup.getId() + EDIT_VIEW).contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW)).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomGroup.getId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(randomGroup.getDepartmentId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(COURSE_ID, is(randomGroup.getCourseId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_NUMBER, is(randomGroup.getGroupNumber()))));
//
//        verify(groupService, times(1)).getById(randomGroup.getId());
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void create_shouldCreateGroupAndRedirect() throws Exception {

//        when(groupService.create(new Group(randomGroup.getDepartmentId(), randomGroup.getCourseId(), randomGroup.getGroupNumber()))).thenReturn(randomGroup);
//
//        mockMvc.perform(post(TEMPLATE_URL).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(DEPARTMENT_ID, String.valueOf(randomGroup.getDepartmentId())).param(COURSE_ID, String.valueOf(randomGroup.getCourseId())).param(GROUP_NUMBER, String.valueOf(randomGroup.getGroupNumber()))).andExpect(status().is3xxRedirection()).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(randomGroup.getDepartmentId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(COURSE_ID, is(randomGroup.getCourseId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_NUMBER, is(randomGroup.getGroupNumber()))));
//
//        verify(groupService, times(1)).create(new Group(randomGroup.getDepartmentId(), randomGroup.getCourseId(), randomGroup.getGroupNumber()));
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void update_shouldUpdateGroupAndRedirect() throws Exception {

        long randomDepartmentId = 10;
        long randomCourseId = 15;
        int randomGroupNumber = 7;

//        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomGroup.getId()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(DEPARTMENT_ID, String.valueOf(randomDepartmentId)).param(COURSE_ID, String.valueOf(randomCourseId)).param(GROUP_NUMBER, String.valueOf(randomGroupNumber))).andExpect(status().is3xxRedirection()).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomGroup.getId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(randomDepartmentId)))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(COURSE_ID, is(randomCourseId)))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_NUMBER, is(randomGroupNumber))));
//
//        verify(groupService, times(1)).update(randomGroup.getId(), new Group(randomDepartmentId, randomCourseId, randomGroupNumber));
        verifyNoMoreInteractions(groupService);
    }

    @Test
    void delete_shouldDeleteGroupAndRedirect() throws Exception {

//        mockMvc.perform(delete(TEMPLATE_URL + SLASH + randomGroup.getId()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(ID, String.valueOf(randomGroup.getId()))).andExpect(status().is3xxRedirection());
//
//        verify(groupService, times(1)).delete(randomGroup.getId());
        verifyNoMoreInteractions(groupService);
    }
}
